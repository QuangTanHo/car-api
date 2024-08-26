package quanli.duan.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import quanli.duan.service.FileStorageService;
import quanli.duan.utils.Constants;

import java.io.IOException;
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

    private final FileStorageService fileStorageService;

    @Value("#{'${PATH_IMAGE}'}")
    private String pathImage;

    @Override
    public ResponseBody<Object> getAllBranch() {
        var brandModels = brandRepository.findAllByIsDelete(Boolean.TRUE);
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
    public ResponseBody<Object> getListBranch(Integer page, Integer size) {
        var mapper = new ObjectMapper();
        var json = mapper.createObjectNode();
        Pageable pageable = PageRequest.of(page-1, size);
        var brandModels = brandRepository.getBrandModelByIsDelete(Boolean.TRUE,pageable);
        if (Objects.isNull(brandModels)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }
        json.putPOJO("page_number", page);
        json.putPOJO("total_records", brandModels.getTotalElements());
        json.putPOJO("page_size", size);
        json.putPOJO("data", brandModels.getContent());
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
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
            .fileId(request.getFileId())
            .href("/xe-".concat(request.getName()))
            .imageUrl(!Strings.isEmpty(request.getFileId()) ? pathImage.concat(request.getFileId()) : request.getImageUrl())
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
    public  ResponseBody<Object> updateBrand(BrandSaveOrUpdateRequest request) throws IOException {

        var brandModel = this.brandRepository.findByBrandIdAndIsDelete(request.getBrandId(), Boolean.TRUE);
        if (Objects.isNull(brandModel)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }
        if(!brandModel.getName().equals(request.getName())){
           this.validateBrandName(request.getName());
        }
         if(!Objects.isNull(brandModel.getFileId())){
             if(!brandModel.getFileId().equals(request.getFileId())){
                this.fileStorageService.deleteFile(brandModel.getFileId());
             }
         }
        brandModel.setName(request.getName());
        brandModel.setImageUrl(!Strings.isEmpty(request.getFileId()) ? pathImage.concat(request.getFileId()) : request.getImageUrl());
        brandModel.setHref("/xe-".concat(request.getName()));
        brandModel.setFileId(request.getFileId());
         brandRepository.save(brandModel);
        var json = new ObjectMapper().createObjectNode();
        json.putPOJO("data",request.getBrandId());

        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }
    @Override
    public ResponseBody<Object> deleteBrandById(Integer BrandId) throws IOException {
        var brandModel = brandRepository.findByBrandIdAndIsDelete(BrandId,Boolean.TRUE);
        if (Objects.isNull(brandModel)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(BRAND_NOT_FOUND.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, BRAND_NOT_FOUND, errorMapping);
        }
        if(!Objects.isNull(brandModel.getFileId())){
            this.fileStorageService.deleteFile(brandModel.getFileId());
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
