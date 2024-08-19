package quanli.duan.dto.response.Car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarMenu {
    Integer carModelId;
    String name;
    String url;
}
