package quanli.duan.entity;

import jakarta.persistence.*;
import lombok.*;
import quanli.duan.repository.CarModelRepository;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = CarModelRepository.TABLE)
public class CarModel extends CommonModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_model_id", nullable = false)
    Integer carModelId;

    @Column(name = "brand_id")
    Integer brandId;

    @Column(name = "car_image_id")
    Integer carImageId;

    @Column(name = "car_name")
    String carName;

    @Column(name = "href")
    String href;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "type")
    String type;

    @Column(name = "price", precision = 20, scale = 3)
    BigDecimal price = new BigDecimal("0.000");


}
