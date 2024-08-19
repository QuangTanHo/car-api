package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quanli.duan.entity.CarImage;

import java.util.List;

public interface CarImageRepository extends JpaRepository<CarImage, Integer> {
    String TABLE ="car_images";
    CarImage findByUrlImage(String name);
    List<CarImage> findByCarModelIdAndIsDelete(Integer carModelId, Boolean isDelete);
}
