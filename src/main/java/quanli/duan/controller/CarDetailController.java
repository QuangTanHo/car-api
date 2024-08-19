package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quanli.duan.repository.CarDetailRepository;
import quanli.duan.service.BrandService;
import quanli.duan.service.CarDetailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1/car-detail")
public class CarDetailController {

    final CarDetailService carDetailService;
    @GetMapping("/get-by-carModelId")
    public ResponseEntity<Object> getCarModelByBrandId( @Param("carModelId") Integer carModelId) {
        return ResponseEntity.ok(carDetailService.getCarDetail(carModelId));
    }
}
