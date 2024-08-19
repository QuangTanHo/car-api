package quanli.duan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import quanli.duan.repository.BrandRepository;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = BrandRepository.TABLE)
public class BrandModel extends CommonModel{
    @Id
    @Column(nullable = false)
    Integer brandId;
    Integer imageId;
    String name;
    String href;
    String imageUrl;
    String type;

}
