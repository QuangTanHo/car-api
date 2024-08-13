package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, String> {
    String TABLE = "role";
}
