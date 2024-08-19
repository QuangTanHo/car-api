package quanli.duan.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quanli.duan.entity.CarDetailModel;
import quanli.duan.entity.CarImage;
import quanli.duan.entity.VersionModel;
import quanli.duan.repository.CarDetailRepository;
import quanli.duan.repository.CarImageRepository;
import quanli.duan.repository.VersionRepository;
import quanli.duan.service.CarDetailService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CarDetailServiceImpl implements CarDetailService {

    private final CarDetailRepository carDetailRepository;
    private final VersionRepository versionRepository;
    private final CarImageRepository carImageRepository;

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

}
