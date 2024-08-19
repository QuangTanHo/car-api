package quanli.duan.entity;

import jakarta.persistence.*;
import lombok.*;
import quanli.duan.repository.BrandRepository;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = BrandRepository.TABLE)
public class BrandModel extends CommonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", nullable = false)
    Integer brandId;

    @Column(name = "image_id")
    Integer imageId;

    @Column(name = "name")
    String name;

    @Column(name = "href")
    String href;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "type")
    String type;

    // Getters and setters (optional)
}

