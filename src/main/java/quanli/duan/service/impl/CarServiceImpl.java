package quanli.duan.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.cars.CarSearchRequest;
import quanli.duan.dto.request.cars.CompareCarRequest;
import quanli.duan.entity.CarDetailModel;
import quanli.duan.entity.CarModel;
import quanli.duan.repository.CarDetailRepository;
import quanli.duan.repository.CarModelRepository;
import quanli.duan.service.CarService;

import java.util.List;

import static quanli.duan.core.response.ResponseStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
   private final CarModelRepository carModelRepository;
   private final CarDetailRepository carDetailRepository;
@Override
    public ResponseBody<Object> getListCarModelByPrice(CarSearchRequest request) {
       var mapper = new ObjectMapper();
       var json = mapper.createObjectNode();
       Pageable pageable = PageRequest.of(request.getPageNumber()-1, request.getPageSize());
       Page<CarModel> listCarModels = carModelRepository.getListCarModelByPrice(request.getCarName(),request.getPriceMin(),request.getPriceMax(),pageable);
       json.putPOJO("page_number", request.getPageNumber());
       json.putPOJO("total_records", listCarModels.getTotalElements());
       json.putPOJO("page_size", request.getPageSize());
       json.putPOJO("data", listCarModels.getContent());
       var response = new ResponseBody<>();
       response.setOperationSuccess(SUCCESS, json);
       return response;

    }
    @Override
   public ResponseBody<Object> getCarModelByBrandId(Integer brandId, Integer page, Integer size) {
      var mapper = new ObjectMapper();
      var json = mapper.createObjectNode();
      Pageable pageable = PageRequest.of(page-1, size);
      Page<CarModel> listCarModels = carModelRepository.findCarModelByBrandIdAndIsDelete(brandId,Boolean.TRUE,pageable);
      json.putPOJO("page_number", page);
      json.putPOJO("total_records", listCarModels.getTotalElements());
      json.putPOJO("page_size", size);
      json.putPOJO("data", listCarModels.getContent());
      var response = new ResponseBody<>();
      response.setOperationSuccess(SUCCESS, json);
      return response;

   }
   @Override
   public ResponseBody<Object> getListCarModelByBrandId(Integer brandId) {
      List<CarModel> CarDetailModels = carModelRepository.findCarModelByBrandIdAndIsDelete(brandId,Boolean.TRUE );
      var response = new ResponseBody<>();
      response.setOperationSuccess(SUCCESS, CarDetailModels);
      return response;
   }

   @Override
   public ResponseBody<Object> copmareCar(CompareCarRequest request) {
      var mapper = new ObjectMapper();
      var json = mapper.createObjectNode();
      CarDetailModel responeDetail1 = carDetailRepository.findByCarModelIdAndIsDelete(request.getCarModelId1(),Boolean.TRUE );
      CarDetailModel responeDetail2 = carDetailRepository.findByCarModelIdAndIsDelete(request.getCarModelId2(),Boolean.TRUE );
      json.putPOJO("car1", responeDetail1);
      json.putPOJO("car2", responeDetail2);
      var response = new ResponseBody<>();
      response.setOperationSuccess(SUCCESS, json);
      return response;
   }


}
