package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.CarDetailModel;

@Repository
public interface CarDetailRepository  extends JpaRepository<CarDetailModel, Integer> {
    String TABLE ="car_details";

    CarDetailModel findByCarDetailName(String name);
    CarDetailModel findByCarModelIdAndIsDelete(Integer carModelId, Boolean isDelete);

}
