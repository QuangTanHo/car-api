package quanli.duan.service;

import quanli.duan.core.response.ResponseBody;

public interface BrandService {
    ResponseBody<Object> getAllBranch();

    ResponseBody<Object> getMenu();
}
