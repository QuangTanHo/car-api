package quanli.duan.service;

import quanli.duan.core.response.ResponseBody;

public interface ShowRoomService {
    ResponseBody<Object> getShowRoomByBrandId(Integer brandId, Integer page, Integer size);


    ResponseBody<Object> getShowRoomByOutstanding(Integer page, Integer size);
}
