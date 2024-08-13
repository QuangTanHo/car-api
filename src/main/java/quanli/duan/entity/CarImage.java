package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.CarImageRepository;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = CarImageRepository.TABLE)
public class CarImage extends CommonModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Integer carImageId;
    @Column(nullable = false)
    Integer carModelId;
    String urlImage;
    String type;
}
