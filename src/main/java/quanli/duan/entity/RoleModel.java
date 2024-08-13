package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.RoleRepository;
import quanli.duan.repository.UserRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = RoleRepository.TABLE)
public class RoleModel {
    @Id
    @Column(nullable = false)
    private String roleId;
    private String roleName;
}
