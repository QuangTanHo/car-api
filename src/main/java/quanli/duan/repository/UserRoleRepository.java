package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.UserRoleModel;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel, String> {
    String TABLE = "user_role";
    List<UserRoleModel> findAllByUserId(String userId);
}
