package quanli.duan.dto.request.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileSearchRequest {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("file_type")
    private String fileType;

    @NotBlank(message = "Page number is not blank")
    @JsonProperty("page_number")
    private String pageNumber;

    @NotBlank(message = "Page size is not blank")
    @JsonProperty("page_size")
    private String pageSize;

    @JsonProperty("sort_by")
    private String sortBy;

    @JsonProperty("sort_direction")
    private String sortDirection;
}
