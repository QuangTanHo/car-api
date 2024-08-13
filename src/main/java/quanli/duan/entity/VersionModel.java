package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.VersionRepository;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = VersionRepository.TABLE)
public class VersionModel extends CommonModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Integer versionId;
    @Column(nullable = false)
    Integer carModelId;
    String versionName ;
    BigDecimal publicPrice = new BigDecimal(0.00);
    BigDecimal onRoadPriceHCM = new BigDecimal(0.00);
    BigDecimal onRoadPriceHN = new BigDecimal(0.00);
    BigDecimal onRoadPriceProvince = new BigDecimal(0.00);
    BigDecimal installmentPayment= new BigDecimal(0.00);
}
