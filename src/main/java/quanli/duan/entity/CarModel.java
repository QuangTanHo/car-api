package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.CarModelRepository;

import javax.persistence.*;
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
    @Column(nullable = false)
    Integer carModelId;
    Integer brandId;
    Integer carImageId;
    String name;
    String href;
    String imageUrl;
    String type;
    BigDecimal price = new BigDecimal(0.000);

}
