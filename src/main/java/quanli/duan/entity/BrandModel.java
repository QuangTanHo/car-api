package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.BrandRepository;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = BrandRepository.TABLE)
public class BrandModel extends CommonModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Integer brandId;
    Integer imageId;
    String name;
    String href;
    String imageUrl;
    String type;

}
