package quanli.duan.dto.request.cars;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompareCarRequest {
    Integer brandId1 ;

    Integer carModelId1 ;

    Integer brandId2 ;

    Integer carModelId2 ;

}
