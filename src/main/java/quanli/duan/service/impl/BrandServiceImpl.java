package quanli.duan.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ErrorData;
import quanli.duan.core.response.ResponseBody;
//import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.repository.BrandRepository;
import quanli.duan.service.BrandService;

import java.util.Objects;

import static quanli.duan.core.response.ResponseStatus.BRANCH_NOT_FOUND;
import static quanli.duan.core.response.ResponseStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

@Override
    public ResponseBody<Object> getAllBranch() {
        var brandModels = brandRepository.findAll();
//        if (Objects.isNull(brandModels)) {
//            var errorMapping = ErrorData.builder()
//                    .errorKey1(BRANCH_NOT_FOUND.getCode())
//                    .build();
//            throw new ServiceSecurityException(HttpStatus.OK, BRANCH_NOT_FOUND, errorMapping);
//        }
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, brandModels);
        return response;
    }


}
