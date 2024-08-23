package quanli.duan.dto.response.Car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quanli.duan.entity.CarImage;
import quanli.duan.entity.VersionModel;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailResponse {
    Integer carDetail;
    String Name;
    List<VersionModel> version ;
    List<CarImage> carImage ;
}
