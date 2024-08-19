package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.ShowRoomRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = ShowRoomRepository.TABLE)
public class ShowRoomModel extends CommonModel{
    @Id
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
    String outstanding;

}
