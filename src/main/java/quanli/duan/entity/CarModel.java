package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.CarModelRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = CarModelRepository.TABLE)
public class CarModel extends CommonModel{
    @Id
    @Column(nullable = false)
    Integer carModelId;
    Integer brandId;
    Integer carImageId;
    String carName;
    String href;
    String imageUrl;
    String type;
    BigDecimal price = new BigDecimal(0.000);

}
