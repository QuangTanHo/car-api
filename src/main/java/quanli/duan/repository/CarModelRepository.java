package quanli.duan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quanli.duan.dto.response.Car.CarMenu;
import quanli.duan.dto.response.brand.BrandMenu;
import quanli.duan.entity.CarModel;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    String TABLE ="car_models";
    CarModel findByCarName(String name);
    @Query("SELECT new quanli.duan.dto.response.Car.CarMenu(c.carModelId, c.carName, c.href) " +
            "FROM  car_models c " +
            "WHERE c.isDelete = :isDelete "+
            "AND (c.brandId = :brandId)")
    List<CarMenu> getListBrandMennu(@Param("isDelete") Boolean isDelete, @Param("brandId") Integer brandId);
    @Query(value = "SELECT * FROM " + TABLE +
            " WHERE carName LIKE %:carName%" +
            " AND ( price BETWEEN :priceMin AND :priceMax ) " +
            " AND  isDelete = '1'", nativeQuery = true)
    Page<CarModel> getListCarModelByPrice(@Param("carName") String carName ,@Param("priceMin") BigDecimal priceMin, @Param("priceMax") BigDecimal priceMax, Pageable pageable);

    Page<CarModel> findCarModelByBrandIdAndIsDelete(Integer brandId ,Boolean isDelete,Pageable pageable);

    List<CarModel> findCarModelByBrandIdAndIsDelete(Integer brandId ,Boolean isDelete);
}
