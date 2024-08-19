package quanli.duan.dto.request.cars;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSearchRequest {

    @JsonProperty("car_name")
    private String carName;

    @JsonProperty("price_min")
    private BigDecimal priceMin;

    @JsonProperty("price_max")
    private BigDecimal priceMax;

    @JsonProperty("page_number")
    private Integer pageNumber;

    @JsonProperty("page_size")
    private Integer pageSize;

}
