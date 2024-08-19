package quanli.duan.entity;

import jakarta.persistence.*;
import lombok.*;
import quanli.duan.repository.CarImageRepository;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = CarImageRepository.TABLE)
public class CarImage extends CommonModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_image_id", nullable = false)
    Integer carImageId;

    @Column(name = "car_model_id", nullable = false)
    Integer carModelId;

    @Column(name = "url_image")
    String urlImage;

    @Column(name = "type")
    String type;
}
