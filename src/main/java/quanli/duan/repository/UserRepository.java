package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.UsersModel;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersModel, String> {
    String TABLE = "user";
    boolean existsByEmail(String email);
    Optional<UsersModel> findByEmail(String email);
}
