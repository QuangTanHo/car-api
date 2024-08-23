package quanli.duan.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ErrorData;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.brand.BrandSaveOrUpdateRequest;
import quanli.duan.dto.response.brand.BrandMenu;
import quanli.duan.entity.BrandModel;
import quanli.duan.entity.CarDetailModel;
import quanli.duan.entity.CarModel;
import quanli.duan.entity.VersionModel;
import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.repository.BrandRepository;
import quanli.duan.repository.CarDetailRepository;
import quanli.duan.repository.CarModelRepository;
import quanli.duan.repository.VersionRepository;
import quanli.duan.service.BrandService;
import quanli.duan.utils.Constants;

import java.util.List;
import java.util.Objects;

import static quanli.duan.core.response.ResponseStatus.*;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final CarModelRepository carModelRepository;
    private final CarDetailRepository carDetailRepository;
    private final VersionRepository versionRepository;
    @Override
    public ResponseBody<Object> getAllBranch() {
        var brandModels = brandRepository.findAll();
        if (Objects.isNull(brandModels)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, brandModels);
        return response;
    }
    @Override
    public ResponseBody<Object> getBrandById(Integer brandId) {
        var brandModels = brandRepository.findByBrandIdAndIsDelete(brandId,Boolean.TRUE);
        if (Objects.isNull(brandModels)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, brandModels);
        return response;
    }
    @Override
    public ResponseBody<Object> getMenu() {
        List<BrandMenu> listBrandMennus = brandRepository.getListBrandMennu(Boolean.TRUE);
        listBrandMennus.forEach(brandMenu -> {
            var listBrandMennu = carModelRepository.getListBrandMennu(Boolean.TRUE, brandMenu.getBrandId());
            if (!listBrandMennu.isEmpty()) {
                brandMenu.setChildrenCar(listBrandMennu);
            }
        });
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, listBrandMennus);
        return response;
    }
    @Override
    public ResponseBody<Object> saveBrand(BrandSaveOrUpdateRequest request){
    this.validateBrandName(request.getName());
    var brand = BrandModel.builder()
            .name(request.getName())
            .type(Constants.ADD_NEW)
            .build();
    BrandModel brandModelsave=  brandRepository.save(brand);
    var json = new ObjectMapper().createObjectNode();
    json.putPOJO("data", brandModelsave);

    var response = new ResponseBody<>();
    response.setOperationSuccess(SUCCESS, json);
    return response;
  }
    @Override
    public  ResponseBody<Object> updateBrand(BrandSaveOrUpdateRequest request){

        var brandModel = this.brandRepository.findByBrandIdAndIsDelete(request.getBrandId(), Boolean.TRUE);
        if (Objects.isNull(brandModel)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }
        this.validateBrandName(request.getName());

        brandModel.setName(request.getName());
        brandModel.setImageUrl(request.getImageUrl());
        brandModel.setHref("/xe-".concat(request.getName()));
        brandModel.setImageId(request.getImageId());
         brandRepository.save(brandModel);
        var json = new ObjectMapper().createObjectNode();
        json.putPOJO("data",request.getBrandId());

        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }
    @Override
    public ResponseBody<Object> deleteBrandById(Integer BrandId) {
        var brandModel = brandRepository.findByBrandIdAndIsDelete(BrandId,Boolean.TRUE);
        if (Objects.isNull(brandModel)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }
        brandModel.setIsDelete(Boolean.FALSE);
        this.brandRepository.save(brandModel);
        List<CarModel> listCarModel = carModelRepository.findCarModelByBrandIdAndIsDelete(BrandId,Boolean.TRUE);
        if (listCarModel.isEmpty()) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }

        listCarModel.stream().forEach( carModel -> {
            carModel.setIsDelete(Boolean.FALSE);
            this.carModelRepository.save(carModel);
            CarDetailModel carDetail = carDetailRepository.findByCarModelIdAndIsDelete(carModel.getCarModelId(),Boolean.TRUE);
            if(!Objects.isNull(carDetail)){
                carDetail.setIsDelete(Boolean.FALSE);
                this.carDetailRepository.save(carDetail);
            }
           List<VersionModel> listVersion =  this.versionRepository.findByCarModelIdAndIsDelete(carModel.getCarModelId(), Boolean.TRUE);
             listVersion.stream().forEach(version ->{
                 version.setIsDelete(Boolean.FALSE);
                 this.versionRepository.save(version);
             });
        });

        var json = new ObjectMapper().createObjectNode();
        json.putPOJO("BrandId", BrandId);
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }

    private void validateBrandName(String brandName){
        var brandModelNameExist = this.brandRepository.existsByNameAndIsDelete(brandName,Boolean.TRUE);
        if (brandModelNameExist) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NAME_EXIST.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NAME_EXIST, errorMapping);
        }
    }
}
