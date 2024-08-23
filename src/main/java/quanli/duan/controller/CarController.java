package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quanli.duan.dto.request.cars.CarSearchRequest;
import quanli.duan.dto.request.cars.CompareCarRequest;
import quanli.duan.repository.CarModelRepository;
import quanli.duan.service.CarService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CarController {
    private final CarService carService;

    @GetMapping("un-auth/car/search-price")
    public ResponseEntity<Object> getListCarModelByPrice(@RequestBody CarSearchRequest request) {
        return ResponseEntity.ok(carService.getListCarModelByPrice(request));
    }
    @GetMapping("un-auth/car/get-by-brandId")
    public ResponseEntity<Object> getCarModelByBrandId(@Param("brandId") Integer brandId ,
                                                       @Param("page") Integer page ,
                                                       @Param("size") Integer size ) {
        return ResponseEntity.ok(carService.getCarModelByBrandId(brandId,page,size));
    }

    @GetMapping("un-auth/car/get-list-by-brandId")
    public ResponseEntity<Object> getCarModelByBrandId(@Param("brandId") Integer brandId  ) {
        return ResponseEntity.ok(carService.getListCarModelByBrandId(brandId));
    }

    @GetMapping("un-auth/car/compare-car")
    public ResponseEntity<Object> getCarModelByBrandId(@RequestBody CompareCarRequest request) {
        return ResponseEntity.ok(carService.copmareCar(request));
    }

}
