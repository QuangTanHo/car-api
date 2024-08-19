package quanli.duan.entity;

import jakarta.persistence.*;
import lombok.*;
import quanli.duan.repository.VersionRepository;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = VersionRepository.TABLE)
public class VersionModel extends CommonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id", nullable = false)
    Integer versionId;

    @Column(name = "car_model_id", nullable = false)
    Integer carModelId;

    @Column(name = "version_name")
    String versionName;

    @Column(name = "type")
    String type;

    @Column(name = "public_price")
    BigDecimal publicPrice = new BigDecimal(0.00);

    @Column(name = "on_road_price_hcm")
    BigDecimal onRoadPriceHCM = new BigDecimal(0.00);

    @Column(name = "on_road_price_hn")
    BigDecimal onRoadPriceHN = new BigDecimal(0.00);

    @Column(name = "on_road_price_province")
    BigDecimal onRoadPriceProvince = new BigDecimal(0.00);

    @Column(name = "installment_payment")
    BigDecimal installmentPayment = new BigDecimal(0.00);
}