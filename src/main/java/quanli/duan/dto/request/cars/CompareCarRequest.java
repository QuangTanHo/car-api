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
    String brandId1 ;

    String carModelId1 ;

    String brandId2 ;

    String carModelId2 ;

}
