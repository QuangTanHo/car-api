package quanli.duan.controller;


import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quanli.duan.dto.request.file.FileSearchRequest;
import quanli.duan.dto.request.file.FileStorageUploadRequest;
import quanli.duan.dto.request.file.ListFileIdRequest;
import quanli.duan.dto.request.file.ListTypeFileRequest;
import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.service.FileStorageService;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FileStorageController {

    private final FileStorageService fileStorageService;
    private final Validator validator;

    @PostMapping(value = "/un-auth/files/upload_file",
    consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> uploadFileStorage(FileStorageUploadRequest request) {
        this.validateRequest(request);
        return ResponseEntity.ok(fileStorageService.uploadFile(request));
    }

    @GetMapping("/un-auth/files/get_info_file_storage/{file_id}")
    public ResponseEntity<Object> getInfoFileStorage(@PathVariable("file_id") String fileId) {
        return ResponseEntity.ok(fileStorageService.getInfoFileStorage(fileId));
    }

    @PostMapping("/un-auth/files/get_info_file_type")
    public ResponseEntity<Object> getInfoFileStorageAllType(@RequestBody ListTypeFileRequest request) {
        return ResponseEntity.ok(fileStorageService.getInfoFileStorageAllType(request));
    }

    @PostMapping("/un-auth/files/get_info_file_storage/list")
    public ResponseEntity<Object> getInfoFileStorageList(@RequestBody ListFileIdRequest request) {
        return ResponseEntity.ok(fileStorageService.getInfoFileStorageInfoList(request));
    }

    @DeleteMapping("/un-auth/files/delete/{file_id}")
    public ResponseEntity<Object> deleteFile(@PathVariable("file_id") String fileId) throws IOException {
        return ResponseEntity.ok(fileStorageService.deleteFile(fileId));
    }

    @DeleteMapping("/un-auth/files/multi/delete")
    public ResponseEntity<Object> deleteMultiFile(@RequestBody ListFileIdRequest listFileIdRequest) {
        return ResponseEntity.ok(fileStorageService.deleteMultiFile(listFileIdRequest));
    }

    @GetMapping(value = "/un-auth/files/download/thumbnail/{fileId}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public ResponseEntity<byte[]> downloadFileWithUrl(@PathVariable String fileId) throws IOException {
        return ResponseEntity.ok(fileStorageService.downloadThumbnailWithUrl(fileId));
    }

    @GetMapping(value = "/un-auth/files/download/original/{fileId}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public ResponseEntity<byte[]> downloadFileOriginalWithUrl(@PathVariable String fileId) throws IOException {
        return ResponseEntity.ok(fileStorageService.downloadOriginalWithUrl(fileId));
    }

    @GetMapping(value = "/un-auth/file_name/download/original/{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public ResponseEntity<byte[]> downloadFileNameOriginalWithUrl(@PathVariable String fileName) throws IOException {
        return ResponseEntity.ok(fileStorageService.downloadFileNameOriginalWithUrl(fileName));
    }

    @PostMapping("/un-auth/files/get_all_file")
    public ResponseEntity<Object> getAllFile(@RequestBody FileSearchRequest request) {
        this.validateRequest(request);
        return ResponseEntity.ok(fileStorageService.getAllFilePage(request));
    }

    private <T> void validateRequest(T request) {
        var violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ServiceSecurityException(violations);
    }
}
