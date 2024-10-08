package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.RoleRepository;
import quanli.duan.repository.UserRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
