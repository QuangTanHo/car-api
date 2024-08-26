package quanli.duan.dto.request.brand;

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
public class BrandSaveOrUpdateRequest {

    @JsonProperty("brandId")
    Integer brandId ;

    @NotBlank(message = "Brand name not blank")
    String name;

    String fileId;

    String href;

    String imageUrl;
}
