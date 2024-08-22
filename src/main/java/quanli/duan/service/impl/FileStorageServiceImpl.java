package quanli.duan.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quanli.duan.core.response.ErrorData;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.file.FileSearchRequest;
import quanli.duan.dto.request.file.FileStorageUploadRequest;
import quanli.duan.dto.request.file.ListFileIdRequest;
import quanli.duan.dto.request.file.ListTypeFileRequest;
import quanli.duan.dto.response.file.FileStorageInfoListResponse;
import quanli.duan.dto.response.file.FileStorageInfoResponse;
import quanli.duan.entity.FileStorageModel;
import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.repository.FileStorageRepository;
import quanli.duan.service.FileStorageService;
import quanli.duan.utils.FileHelperService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;

import static org.apache.http.entity.ContentType.*;
import static quanli.duan.core.response.ResponseStatus.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final FileStorageRepository fileStorageRepository;
    private final FileHelperService fileHelperService;
    final static String BASE_PATH = "./src";
    final static String ORIGINAL = "original/";
    final static String THUMBNAIL = "thumbnail/";
    private static final String DEFAULT_SORT_FIELD = "createDate";

    @Override
    public ResponseBody<Object> uploadFile(FileStorageUploadRequest request) {
        var response = new ResponseBody<>();
        var file = request.getFile();
        var rawFileName = request.getFile_name();
        var existsByRawFileName = fileStorageRepository.existsByRawFileName(rawFileName);
        var subDirectory = this.getSubDirectory(request.getFile_directory());
        var fileId = UUID.randomUUID().toString().replaceAll("-", "");
        var currentDateTime = LocalDateTime.now();
        try {
            if (existsByRawFileName) {
                rawFileName = fileId + "-" + rawFileName;
            }
            String directoryPath = BASE_PATH + subDirectory;
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String docTypeId = request.getDoc_type_id();
            var fileName = fileId + "." + fileExtension;
            if (Objects.nonNull(docTypeId)) {
                fileName = docTypeId + "-" + fileName;
            }

            byte[] bytes = new byte[0];
            bytes = file.getBytes();
            if (Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType(),
                    IMAGE_WEBP.getMimeType(), IMAGE_SVG.getMimeType()).contains(file.getContentType())) {
                subDirectory = subDirectory + ORIGINAL;
                String directoryPathOriginal = directoryPath + ORIGINAL;
                this.createDirIfNotExist(directoryPathOriginal);
                Files.write(Paths.get(directoryPathOriginal + fileName), bytes);

                String directoryPathThumbnail = directoryPath + THUMBNAIL;
                createDirIfNotExist(directoryPathThumbnail);
                File thumbnail = new File(directoryPathThumbnail + fileName);
                BufferedImage sourceImage = ImageIO.read(new File(directoryPathOriginal + fileName));
                Image resultingImage = sourceImage.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
                ImageIO.write(convertToBufferedImage(resultingImage), fileExtension, thumbnail);
            } else {
                createDirIfNotExist(directoryPath);
                Files.write(Paths.get(directoryPath + fileName), bytes);
            }

            FileStorageModel fileStorageModel = FileStorageModel.builder()
                    .fileId(fileId)
                    .fileDirectory(subDirectory)
                    .rawFileName(rawFileName)
                    .fileName(fileName)
                    .fileExtension(fileExtension)
                    .description(request.getDescription())
                    .fileType(request.getType())
                    .createDate(currentDateTime)
                    .build();
            fileStorageRepository.save(fileStorageModel);
            var json = new ObjectMapper().createObjectNode();
            json.putPOJO("file_id", fileId);
            json.putPOJO("raw_file_name", rawFileName);
            response.setOperationSuccess(SUCCESS, json);
            return response;
        } catch (Exception e) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(UPLOAD_FILE_FAIL.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, UPLOAD_FILE_FAIL, errorMapping);
        }
    }

    public ResponseBody<Object> getInfoFileStorage(String fileId) {
        var fileStorage = fileStorageRepository.findById(fileId).orElseThrow(() -> {
            ErrorData errorData = ErrorData.builder()
                    .errorKey2(FILE_NOT_FOUND.getCode())
                    .build();
            return new ServiceSecurityException(HttpStatus.OK, FILE_NOT_FOUND, errorData);
        });

        FileStorageInfoResponse fileStorageInfo = FileStorageInfoResponse.builder()
                .fileId(fileId)
                .fileDirectory(fileStorage.getFileDirectory())
                .rawFileName(fileStorage.getRawFileName())
                .fileName(fileStorage.getFileName())
                .description(fileStorage.getDescription())
                .fileExtension(fileStorage.getFileExtension())
                .type(fileStorage.getFileType())
                .build();

        ResponseBody<Object> response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, fileStorageInfo);
        return response;
    }

    public ResponseBody<Object> getInfoFileStorageInfoList(ListFileIdRequest request) {
        List<FileStorageModel> fileStorageList;
        if (request.getFileIdList().isEmpty()) {
            fileStorageList = fileStorageRepository.findAll();
        } else {
            fileStorageList = fileStorageRepository.findAllByFileId(request.getFileIdList());
        }
        return getListFileStorage(fileStorageList);
    }

    @Override
    public ResponseBody<Object> getInfoFileStorageAllType(ListTypeFileRequest request) {
        List<FileStorageModel> fileStorageList;
        if (request.getType().isEmpty()) {
            fileStorageList = fileStorageRepository.findAll();
        } else {
            fileStorageList = fileStorageRepository.findAllByFileType(request.getType());
        }

        return getListFileStorage(fileStorageList);
    }

    @Override
    public ResponseBody<Object> getAllFilePage(FileSearchRequest request) {
        var mapper = new ObjectMapper();
        var json = mapper.createObjectNode();

        Pageable pageable;

        if (request.getSortBy() == null || request.getSortBy().isEmpty()) {
            request.setSortBy(DEFAULT_SORT_FIELD);
        }

        if (request.getSortDirection() == null || request.getSortDirection().isEmpty()) {
            request.setSortDirection("asc");
        }

        if (request.getSortDirection().equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(Integer.parseInt(request.getPageNumber()) - 1, Integer.parseInt(request.getPageSize()), Sort.by(request.getSortBy()).descending());
        } else {
            pageable = PageRequest.of(Integer.parseInt(request.getPageNumber()) - 1, Integer.parseInt(request.getPageSize()), Sort.by(request.getSortBy()).ascending());
        }

        Page<FileStorageModel> listFilePage = fileStorageRepository.findByFileNameAndFileType(request.getFileName(), request.getFileType(), pageable);

        var listFile = listFilePage.getContent();

        var listFileModel = listFile.stream().map(fileStorage ->
                FileStorageInfoResponse.builder()
                        .fileId(fileStorage.getFileId())
                        .fileDirectory(fileStorage.getFileDirectory())
                        .rawFileName(fileStorage.getRawFileName())
                        .fileName(fileStorage.getFileName())
                        .description(fileStorage.getDescription())
                        .fileExtension(fileStorage.getFileExtension())
                        .type(fileStorage.getFileType())
                        .build());


        json.putPOJO("page_number", request.getPageNumber());
        json.putPOJO("total_records", listFilePage.getTotalElements());
        json.putPOJO("page_size", request.getPageSize());
        json.putPOJO("list_file", listFileModel);

        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);

        return response;
    }

    @Override
    public byte[] downloadFileNameOriginalWithUrl(String rawFileName) throws IOException {
        FileStorageModel fileStorageModel = fileStorageRepository.findByRawFileName(rawFileName);
        if (fileStorageModel == null) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(FILE_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, FILE_NOT_FOUND, errorMapping);
        }

        String fileDirectory = fileStorageModel.getFileDirectory();

        File file = new File(BASE_PATH + fileDirectory + fileStorageModel.getFileName());
        byte[] bytes = fileHelperService.readAllBytes(file.toPath());
        HttpHeaders headers = getHttpHeaders(fileStorageModel.getRawFileName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes).getBody();
    }

    private ResponseBody<Object> getListFileStorage(List<FileStorageModel> fileStorageList) {
        List<FileStorageInfoResponse> fileStorageInfoRes = new ArrayList<>();

        fileStorageList.forEach(fileStorage -> fileStorageInfoRes.add(FileStorageInfoResponse.builder()
                .fileId(fileStorage.getFileId())
                .fileDirectory(fileStorage.getFileDirectory())
                .rawFileName(fileStorage.getRawFileName())
                .fileName(fileStorage.getFileName())
                .description(fileStorage.getDescription())
                .fileExtension(fileStorage.getFileExtension())
                .type(fileStorage.getFileType())
                .build()));
        ResponseBody<Object> response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, FileStorageInfoListResponse.builder().fileStorageInfoList(fileStorageInfoRes).build());
        return response;
    }

    @Transactional
    public ResponseBody<Object> deleteFile(String fileId) throws IOException {
        var response = new ResponseBody<>();
        var fileStorage = fileStorageRepository.findById(fileId).orElseThrow(
                () -> {
                    ErrorData errorData = ErrorData.builder()
                            .errorKey2(FILE_NOT_FOUND.getCode())
                            .build();
                    return new ServiceSecurityException(HttpStatus.OK, FILE_NOT_FOUND, errorData);
                }
        );

        String fileExtension = fileStorage.getFileExtension();
        if (fileExtension.equalsIgnoreCase("png") || fileExtension.equalsIgnoreCase("jpg") ||
                fileExtension.equalsIgnoreCase("jpeg") || fileExtension.equalsIgnoreCase("gif") ||
                fileExtension.equalsIgnoreCase("webp")) {
            String fileDirectory = fileStorage.getFileDirectory().replace("/original", "/thumbnail");
            Path thumbnailPath = Paths.get(BASE_PATH + fileDirectory + fileStorage.getFileName());
            fileHelperService.deleteIfExists(thumbnailPath);
        }

        fileStorageRepository.deleteById(fileId);
        // delete file
        Path myPath = Paths.get(BASE_PATH + fileStorage.getFileDirectory() + fileStorage.getFileName());
        fileHelperService.deleteIfExists(myPath);
        var json = new ObjectMapper().createObjectNode();
        json.putPOJO("file_id", fileId);
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }

    @Override
    @Transactional
    public ResponseBody<Object> deleteMultiFile(ListFileIdRequest listFileIdRequest) {
        List<String> listFileIdRemoved = new ArrayList<>();
        List<FileStorageModel> fileStorageModelList = fileStorageRepository.findAllByFileId(listFileIdRequest.getFileIdList());
        fileStorageModelList.stream()
                .map(FileStorageModel::getFileId)
                .toList()
                .forEach(f ->
                        {
                            try {
                                deleteFile(f);
                                listFileIdRemoved.add(f);
                            } catch (IOException e) {
                                log.info("Delete fail with file id: " + f);
                            }
                        }
                );

        var json = new ObjectMapper().createObjectNode();
        json.putPOJO("list_file_id_removed", listFileIdRemoved);
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }

    @Override
    public byte[] downloadThumbnailWithUrl(String fileId) throws IOException {
        FileStorageModel fileStorage = fileStorageRepository.findById(fileId).orElseThrow(() -> {
            var errorMapping = ErrorData.builder()
                    .errorKey1(FILE_NOT_FOUND.getCode())
                    .build();
            return new ServiceSecurityException(HttpStatus.OK, FILE_NOT_FOUND, errorMapping);
        });
        String fileDirectory = fileStorage.getFileDirectory().replace(ORIGINAL, THUMBNAIL);

        File file = new File(BASE_PATH + fileDirectory + fileStorage.getFileName());
        byte[] bytes = fileHelperService.readAllBytes(file.toPath());
        HttpHeaders headers = getHttpHeaders(fileStorage.getRawFileName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes).getBody();
    }

    @Override
    public byte[] downloadOriginalWithUrl(String fileId) throws IOException {
        FileStorageModel fileStorageModel = fileStorageRepository.findById(fileId).orElseThrow(() -> {
            var errorMapping = ErrorData.builder()
                    .errorKey1(FILE_NOT_FOUND.getCode())
                    .build();
            return new ServiceSecurityException(HttpStatus.OK, FILE_NOT_FOUND, errorMapping);
        });
        String fileDirectory = fileStorageModel.getFileDirectory();

        File file = new File(BASE_PATH + fileDirectory + fileStorageModel.getFileName());
        byte[] bytes = fileHelperService.readAllBytes(file.toPath());
        HttpHeaders headers = getHttpHeaders(fileStorageModel.getRawFileName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes).getBody();
    }


    public String getSubDirectory(String req) {
        var subDirectory = req;
        if (Objects.isNull(subDirectory)) {
            subDirectory = "";
        } else if (!"/".equals(subDirectory.charAt(subDirectory.length() - 1))) {
            subDirectory = subDirectory + "/";
        }
        return subDirectory;
    }

    private void createDirIfNotExist(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private BufferedImage convertToBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bufferedImage;
    }

    private static HttpHeaders getHttpHeaders(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return headers;
    }
}
