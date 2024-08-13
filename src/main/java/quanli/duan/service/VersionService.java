package quanli.duan.service;

import quanli.duan.core.response.ResponseBody;

public interface VersionService {
    ResponseBody<Object> getAllVersionByBrandId(Integer brandId);
}
