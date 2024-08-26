package quanli.duan.service;

import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.cars.CarSearchRequest;
import quanli.duan.dto.request.cars.CompareCarRequest;

import java.math.BigDecimal;

public interface CarService {
    ResponseBody<Object> getListCarModelByPrice(CarSearchRequest request);

    ResponseBody<Object> getCarModelByBrandId(Integer brandId, Integer page, Integer size);

    ResponseBody<Object> getListCarModelByBrandId(Integer brandId);

    ResponseBody<Object> compareCar(CompareCarRequest request);
}
