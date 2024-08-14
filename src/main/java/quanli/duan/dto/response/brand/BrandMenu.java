package quanli.duan.dto.response.brand;

import lombok.Data;
import lombok.NoArgsConstructor;
import quanli.duan.dto.response.Car.CarMenu;

import java.util.List;

@Data
@NoArgsConstructor
public class BrandMenu {
    Integer brandId;
    String name;
    String url;
    List<CarMenu> childrenCar;

    public BrandMenu(Integer brandId, String name, String url) {
        this.brandId = brandId;
        this.name = name;
        this.url = url;
    }

}
