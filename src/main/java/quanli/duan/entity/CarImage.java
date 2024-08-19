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
    @Column(nullable = false)
    Integer carImageId;
    @Column(nullable = false)
    Integer carModelId;
    String urlImage;
    String type;
}
