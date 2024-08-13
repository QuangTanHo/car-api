package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.ShowRoomRepository;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = ShowRoomRepository.TABLE)
public class ShowRoomModel extends CommonModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Integer showRoomId;
    Integer brandId;
    String name;
    String urlImage;
    String href;
    String hotLine;
    String address;
    String area;
    String city;
    String type;

}
