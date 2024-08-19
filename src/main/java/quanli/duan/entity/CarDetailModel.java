package quanli.duan.entity;

import jakarta.persistence.*;
import lombok.*;
import quanli.duan.repository.BrandRepository;
import quanli.duan.repository.CarDetailRepository;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = CarDetailRepository.TABLE)
public class CarDetailModel extends CommonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_detail_id", nullable = false)
    Integer carDetailId;

    @Column(name = "car_model_id", nullable = false)
    Integer carModelId;

    @Column(name = "car_detail_name")
    String carDetailName;

    @Column(name = "brand_name")
    String brandname;

    @Column(name = "type_of_vehicle")
    String typeOfvehicle;

    @Column(name = "segment")
    String segment;

    @Column(name = "origin")
    String origin;

    @Column(name = "price_range")
    String priceRange;

    @Column(name = "seat")
    String seat;

    @Column(name = "hww")
    String HWW;

    @Column(name = "ground_clearance")
    String groundClearance;

    @Column(name = "wheels_and_tires")
    String wheelsAndTires;

    @Column(name = "engine")
    String engine;

    @Column(name = "power")
    String power;

    @Column(name = "torque")
    String torque;

    @Column(name = "transmission")
    String transmission;

    @Column(name = "drive_system")
    String driveSystem;

    @Column(name = "suspension_system")
    String suspensionSystem;

    @Column(name = "rear_brake")
    String rearBrake;

    @Column(name = "brake_technology")
    String brakeTechnology;

    @Column(name = "type")
    String type;
}
