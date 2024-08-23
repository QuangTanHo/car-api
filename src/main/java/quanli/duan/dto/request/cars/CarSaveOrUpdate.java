package quanli.duan.dto.request.cars;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSaveOrUpdate {
    Integer carDetailId;
    Integer carModelId;
    Integer brandId;
    String carDetailName;
    String brandname;
    BigDecimal price;
    String typeOfvehicle;
    String segment;
    String origin;
    String priceRange;
    String seat;
    String HWW;
    String groundClearance;
    String wheelsAndTires;
    String engine;
    String power;
    String torque;
    String transmission;
    String driveSystem;
    String suspensionSystem;
    String rearBrake;
    String brakeTechnology;
    String type;
    String outstanding;
    String exterior ;
    String interior ;
    String operate ;
    String safeAndComfortable ;

}
