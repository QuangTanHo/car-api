package quanli.duan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import quanli.duan.repository.VersionRepository;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = VersionRepository.TABLE)
public class VersionModel extends CommonModel{
    @Id
    @Column(nullable = false)
    Integer versionId;
    @Column(nullable = false)
    Integer carModelId;
    String versionName ;
    String type ;
    BigDecimal publicPrice = new BigDecimal(0.00);
    BigDecimal onRoadPriceHCM = new BigDecimal(0.00);
    BigDecimal onRoadPriceHN = new BigDecimal(0.00);
    BigDecimal onRoadPriceProvince = new BigDecimal(0.00);
    BigDecimal installmentPayment= new BigDecimal(0.00);
}
