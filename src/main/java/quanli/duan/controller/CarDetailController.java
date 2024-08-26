package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quanli.duan.service.CarDetailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CarDetailController {

    final CarDetailService carDetailService;
    @GetMapping("un-auth/car-detail/get-by-carModelId")
    public ResponseEntity<Object> getCarModelByBrandId( @Param("carModelId") Integer carModelId) {
        return ResponseEntity.ok(carDetailService.getCarDetail(carModelId));
    }

}
