package quanli.duan.entity;

import jakarta.persistence.*;
import lombok.*;
import quanli.duan.repository.ShowRoomRepository;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = ShowRoomRepository.TABLE)
public class ShowRoomModel extends CommonModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "show_room_id", nullable = false)
    Integer showRoomId;

    @Column(name = "brand_id")
    Integer brandId;

    @Column(name = "name")
    String name;

    @Column(name = "url_image")
    String urlImage;

    @Column(name = "href")
    String href;

    @Column(name = "hotline")
    String hotLine;

    @Column(name = "address")
    String address;

    @Column(name = "area")
    String area;

    @Column(name = "city")
    String city;

    @Column(name = "type")
    String type;

    @Column(name = "outstanding")
    String outstanding;

}
