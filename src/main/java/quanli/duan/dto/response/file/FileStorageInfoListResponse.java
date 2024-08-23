package quanli.duan.dto.response.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStorageInfoListResponse {

    @JsonProperty("file_storage_info_list")
    private List<FileStorageInfoResponse> fileStorageInfoList;
}
