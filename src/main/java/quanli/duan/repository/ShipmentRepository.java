package quanli.duan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quanli.duan.entity.ShipmentModel;

@Repository
public interface ShipmentRepository extends JpaRepository<ShipmentModel, String> {
    String TABLE = "shipment";
}
