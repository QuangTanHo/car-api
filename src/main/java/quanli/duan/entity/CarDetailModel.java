package quanli.duan.entity;

import lombok.*;
import quanli.duan.repository.CarDetailRepository;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = CarDetailRepository.TABLE)
public class CarDetailModel extends CommonModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Integer carDetailId;
    @Column(nullable = false)
    Integer carModelId;
    String carDetailName;
    String brandname;
    String typeOfvehicle;
    String segment;
    String origin;
    String priceRange;
//    BigDecimal priceRangeMin = new BigDecimal(0.000);
//    BigDecimal priceRangeMax = new BigDecimal(0.000);
//    chỗ ngồi
    String seat;
    String HWW;
//    Double width;
//    Double weight;
    //    Khoảng sáng gầm xe
    String groundClearance;
    //    Vành và Lốp xe
    String wheelsAndTires;
    //    Động cơ
    String engine;
    //    Công suất
    String power;
    //    Mô men xoắn
    String torque;
    //    Hộp số
    String transmission;
    //    Hệ thống dẫn động
    String driveSystem;
    //    Hệ thống treo trước/sau
    String suspensionSystem;
    //    Phanh trước/sau
    String rearBrake;
    //    Phanh trước/sau
    String brakeTechnology;

    String type;
}
