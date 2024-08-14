package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quanli.duan.dto.response.Car.CarMenu;
import quanli.duan.dto.response.brand.BrandMenu;
import quanli.duan.entity.CarModel;

import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    String TABLE ="car_models";
    CarModel findByName(String name);
    @Query("SELECT new quanli.duan.dto.response.Car.CarMenu(c.carModelId, c.name, c.href) " +
            "FROM  car_models c " +
            "WHERE c.isActive = :isActive "+
            "AND (c.brandId = :brandId)")
    List<CarMenu> getListBrandMennu(@Param("isActive") Boolean isActive, @Param("brandId") Integer brandId);
}
