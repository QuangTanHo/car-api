package quanli.duan.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import quanli.duan.dto.response.brand.BrandMenu;
import quanli.duan.entity.BrandModel;

import java.util.List;


@Repository
public interface BrandRepository extends JpaRepository<BrandModel, Integer> {
    String TABLE ="brand";
    BrandModel findByNameAndIsDelete(String name,Boolean isDelete);
    List<BrandModel> findAllByIsDelete(Boolean isDelete);
    Page<BrandModel> getBrandModelByIsDelete(Boolean isDelete, Pageable pageable );
    @Query("SELECT new quanli.duan.dto.response.brand.BrandMenu(b.brandId, b.name, b.href) " +
            "FROM  brand b " +
            "WHERE b.isDelete = :isDelete ")
    List<BrandMenu> getListBrandMennu(@Param("isDelete") Boolean isDelete);
    List<BrandMenu> getBrandModelByBrandIdAndIsDelete( Integer brandId ,Boolean isDelete);
    boolean existsByNameAndIsDelete(String name, Boolean isDelete);
    BrandModel findByBrandIdAndIsDelete(Integer brandId , Boolean isDelete);
}
