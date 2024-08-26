package quanli.duan.dto.request.cars;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionSaveOrUpdate {
    Integer versionId;
    Integer carModelId;
    String versionName;
    String type;
    BigDecimal publicPrice = new BigDecimal(0.00);
    BigDecimal onRoadPriceHCM = new BigDecimal(0.00);
    BigDecimal onRoadPriceHN = new BigDecimal(0.00);
    BigDecimal onRoadPriceProvince = new BigDecimal(0.00);
    BigDecimal installmentPayment = new BigDecimal(0.00);
}
