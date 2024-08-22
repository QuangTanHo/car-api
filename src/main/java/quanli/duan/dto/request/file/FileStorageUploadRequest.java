package quanli.duan.dto.request.file;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import quanli.duan.enumeration.TypeImage;
import quanli.duan.utils.EnumValue;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileStorageUploadRequest {
    @NotNull(message = "File is not null")
    private MultipartFile file;

    @NotBlank(message = "File name is not null")
    private String file_name;
    private String description;
    private String file_directory;
    private String doc_type_id;

    @NotBlank(message = "Type is not null")
    @EnumValue(name = "type", enumClass = TypeImage.class)
    private String type;
}
