package quanli.duan.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.repository.VersionRepository;
import quanli.duan.service.VersionService;

import static quanli.duan.core.response.ResponseStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {
    private  final VersionRepository versionRepository;
     @Override
    public ResponseBody<Object> getAllVersionByBrandId(Integer brandId) {
        var versionRespones = versionRepository.findVersionModelByBrandId(brandId);
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, versionRespones);
        return response;
    }
}
