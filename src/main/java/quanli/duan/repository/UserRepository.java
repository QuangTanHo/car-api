package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.UserModel;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    String TABLE = "user";
    boolean existsByEmail(String email);
    Optional<UserModel> findByEmail(String email);
}
