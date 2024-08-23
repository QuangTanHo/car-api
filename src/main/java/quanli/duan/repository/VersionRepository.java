package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quanli.duan.dto.response.version.versionRespone;
import quanli.duan.entity.VersionModel;

import java.util.List;


@Repository
public interface VersionRepository extends JpaRepository<VersionModel, Integer> {
    String TABLE ="version_cars";

    VersionModel findByVersionName(String name);

@Query("SELECT new quanli.duan.dto.response.version.versionRespone( v.versionId , v.versionName,m.carName, b.name,  d.segment, v.installmentPayment) " +
        "FROM version_cars v, car_models m, car_details d, brand b " +
        "WHERE v.carModelId = m.carModelId " +
        "AND m.carModelId = d.carModelId " +
        "AND m.brandId = b.brandId " +
        "AND v.isDelete = :isDelete " +
        "AND (:brandId IS NULL OR b.brandId = :brandId)")
    List<versionRespone> findVersionModelByBrandId (@Param("brandId") Integer brandId, @Param("isDelete") Boolean isDelete);

    List<VersionModel> findByCarModelIdAndIsDelete(Integer carModelId, Boolean isDelete);
}
