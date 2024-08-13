package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.BrandModel;

import java.util.List;


@Repository
public interface BrandRepository extends JpaRepository<BrandModel, Integer> {
    String TABLE ="brand";
    BrandModel findByName(String name);
    List<BrandModel> findAll();
}
