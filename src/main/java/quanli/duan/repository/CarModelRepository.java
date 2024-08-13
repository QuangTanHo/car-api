package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.CarModel;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
    String TABLE ="car_models";
    CarModel findByName(String name);
}
