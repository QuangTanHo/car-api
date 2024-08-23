package quanli.duan.service;



import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.file.FileSearchRequest;
import quanli.duan.dto.request.file.FileStorageUploadRequest;
import quanli.duan.dto.request.file.ListFileIdRequest;
import quanli.duan.dto.request.file.ListTypeFileRequest;

import java.io.IOException;

public interface FileStorageService {

    ResponseBody<Object> uploadFile(FileStorageUploadRequest request);

    ResponseBody<Object> deleteMultiFile(ListFileIdRequest listFileIdRequest);

    byte[] downloadThumbnailWithUrl(String fileId) throws IOException;

    byte[] downloadOriginalWithUrl(String fileId) throws IOException;

    ResponseBody<Object> deleteFile(String fileId) throws IOException;

    ResponseBody<Object> getInfoFileStorage(String fileId);

    ResponseBody<Object> getInfoFileStorageInfoList(ListFileIdRequest request);

    ResponseBody<Object> getInfoFileStorageAllType(ListTypeFileRequest request);

    ResponseBody<Object> getAllFilePage(FileSearchRequest request);

    byte[] downloadFileNameOriginalWithUrl(String fileName) throws IOException;
}
