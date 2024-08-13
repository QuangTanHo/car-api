package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.UserRoleRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = UserRoleRepository.TABLE)
public class UserRoleModel {
    @Id
    @Column(nullable = false)
    private String userRoleId;
    private String userId;
    private String roleName;

    public static UserRoleModel of(String userId, String roleName) {
        return UserRoleModel.builder()
                .userRoleId(UUID.randomUUID().toString().replaceAll("-",""))
                .userId(userId)
                .roleName(roleName)
                .build();
    }
}
