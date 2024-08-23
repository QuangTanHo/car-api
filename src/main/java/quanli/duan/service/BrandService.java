package quanli.duan.service;

import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.brand.BrandSaveOrUpdateRequest;

public interface BrandService {
    ResponseBody<Object> getAllBranch();

    ResponseBody<Object> getBrandById(Integer brandId);

    ResponseBody<Object> getMenu();

    ResponseBody<Object> saveBrand(BrandSaveOrUpdateRequest request);

    ResponseBody<Object> updateBrand(BrandSaveOrUpdateRequest request);

    ResponseBody<Object> deleteBrandById(Integer BrandId);
}
