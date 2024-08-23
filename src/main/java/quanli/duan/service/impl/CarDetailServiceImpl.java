package quanli.duan.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ErrorData;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.cars.CarSaveOrUpdate;
import quanli.duan.entity.*;
import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.repository.CarDetailRepository;
import quanli.duan.repository.CarImageRepository;
import quanli.duan.repository.CarModelRepository;
import quanli.duan.repository.VersionRepository;
import quanli.duan.service.CarDetailService;
import quanli.duan.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static quanli.duan.core.response.ResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CarDetailServiceImpl implements CarDetailService {

    private final CarDetailRepository carDetailRepository;
    private final VersionRepository versionRepository;
    private final CarImageRepository carImageRepository;

    private final CarModelRepository carModelRepository;

    @Override
    public Map<String, Object> getCarDetail(Integer carModelId) {
        CarDetailModel  carDetailModel =this.carDetailRepository.findByCarModelIdAndIsDelete(carModelId,Boolean.TRUE);
        List<VersionModel> versions = versionRepository.findByCarModelIdAndIsDelete(carModelId,Boolean.TRUE);
        List<CarImage> carImages = carImageRepository.findByCarModelIdAndIsDelete(carModelId,Boolean.TRUE);

        Map<String, Object> response = new HashMap<>();
        response.put("carDetail", carDetailModel);
        response.put("versions", versions);
        response.put("images", carImages);
        return response;
    }
    public ResponseBody<Object> saveCar(CarSaveOrUpdate request){
        var existCarName = carModelRepository.existsByCarNameAndBrandIdAndIsDelete(request.getCarDetailName(), request.getBrandId(), Boolean.TRUE);
        if (existCarName) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }
        var car = CarModel.builder()
                .carName(request.getCarDetailName())
                .brandId(request.getBrandId())
                .imageUrl("")
                .type(Constants.ADD_NEW)
                .build();
        CarModel carModel = carModelRepository.save(car);
        var carDetail = CarDetailModel.builder()
                .carModelId(carModel.getCarModelId())
                .carDetailName(request.getCarDetailName())
                .typeOfvehicle(request.getTypeOfvehicle())
                .segment(request.getSegment())
                .origin(request.getOrigin())
                .priceRange(request.getPriceRange())
                .seat(request.getSeat())
                .HWW(request.getHWW())
                .groundClearance(request.getGroundClearance())
                .wheelsAndTires(request.getWheelsAndTires())
                .engine(request.getEngine())
                .power(request.getPower())
                .torque(request.getTorque())
                .transmission(request.getTransmission())
                .driveSystem(request.getDriveSystem())
                .suspensionSystem(request.getSuspensionSystem())
                .rearBrake(request.getRearBrake())
                .brakeTechnology(request.getBrakeTechnology())
                .type(Constants.ADD_NEW)
                .outstanding(request.getOutstanding())
                .exterior(request.getExterior())
                .interior(request.getInterior())
                .operate(request.getOperate())
                .safeAndComfortable(request.getSafeAndComfortable())
                .build();
        this.carDetailRepository.save(carDetail);

        var json = new ObjectMapper().createObjectNode();
        json.putPOJO("data", carModel);
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }

    public ResponseBody<Object> updateCar(CarSaveOrUpdate request){
        var existCarName = carModelRepository.existsByCarNameAndBrandIdAndIsDelete(request.getCarDetailName(), request.getBrandId(), Boolean.TRUE);
        if (existCarName) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }

        var car = this.carModelRepository.findByCarModelIdAndIsDelete(request.getCarModelId(), Boolean.TRUE);
        if (Objects.isNull(car)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(CAR_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, CAR_NOT_FOUND, errorMapping);
        }
        car.setCarName(request.getCarDetailName());
        car.setBrandId(request.getBrandId());
        car.setImageUrl("");
        car.setType(Constants.ADD_NEW);

        var carDetail = this.carDetailRepository.findByCarModelIdAndIsDelete(request.getCarModelId(), Boolean.TRUE);
        if (Objects.isNull(carDetail)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(CAR_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, CAR_NOT_FOUND, errorMapping);
        }

        carDetail.setCarModelId(request.getCarModelId());
        carDetail.setCarDetailName(request.getCarDetailName());
        carDetail.setTypeOfvehicle(request.getTypeOfvehicle());
        carDetail.setSegment(request.getSegment());
        carDetail.setOrigin(request.getOrigin());
        carDetail.setPriceRange(request.getPriceRange());
        carDetail.setSeat(request.getSeat());
        carDetail.setHWW(request.getHWW());
        carDetail.setGroundClearance(request.getGroundClearance());
        carDetail.setWheelsAndTires(request.getWheelsAndTires());
        carDetail.setEngine(request.getEngine());
        carDetail.setPower(request.getPower());
        carDetail.setTorque(request.getTorque());
        carDetail.setTransmission(request.getTransmission());
        carDetail.setDriveSystem(request.getDriveSystem());
        carDetail.setSuspensionSystem(request.getSuspensionSystem());
        carDetail.setRearBrake(request.getRearBrake());
        carDetail.setBrakeTechnology(request.getBrakeTechnology());
        carDetail.setType(Constants.ADD_NEW);
        carDetail.setOutstanding(request.getOutstanding());
        carDetail.setExterior(request.getExterior());
        carDetail.setInterior(request.getInterior());
        carDetail.setOperate(request.getOperate());
        carDetail.setSafeAndComfortable(request.getSafeAndComfortable());

        this.carModelRepository.save(car);
        this.carDetailRepository.save(carDetail);
        var json = new ObjectMapper().createObjectNode();
        json.putPOJO("data", car);
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }
    public ResponseBody<Object> deleteCar(Integer carModelId) {
        CarModel carModel = this.carModelRepository.findByCarModelIdAndIsDelete(carModelId, Boolean.TRUE);
        CarDetailModel carDetail = this.carDetailRepository.findByCarModelIdAndIsDelete(carModelId, Boolean.TRUE);
        if (Objects.isNull(carModel) || Objects.isNull(carDetail)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(CAR_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, CAR_NOT_FOUND, errorMapping);
        }
        carModel.setIsDelete(Boolean.FALSE);
        carDetail.setIsDelete(Boolean.FALSE);
        this.carModelRepository.save(carModel);
        this.carDetailRepository.save(carDetail);
        var json = new ObjectMapper().createObjectNode();
        json.putPOJO("carModelId", carModelId);
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }
}
