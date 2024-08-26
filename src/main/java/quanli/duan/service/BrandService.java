package quanli.duan.service;

import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.brand.BrandSaveOrUpdateRequest;

import java.io.IOException;

public interface BrandService {
    ResponseBody<Object> getAllBranch();

    ResponseBody<Object> getListBranch(Integer page, Integer size);

    ResponseBody<Object> getBrandById(Integer brandId);

    ResponseBody<Object> getMenu();

    ResponseBody<Object> saveBrand(BrandSaveOrUpdateRequest request);

    ResponseBody<Object> updateBrand(BrandSaveOrUpdateRequest request) throws IOException;

    ResponseBody<Object> deleteBrandById(Integer BrandId) throws IOException;
}
