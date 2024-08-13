package quanli.duan.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.entity.ShowRoomModel;
import quanli.duan.repository.ShowRoomRepository;
import quanli.duan.service.ShowRoomService;

import java.util.List;
import java.util.Objects;

import static quanli.duan.core.response.ResponseStatus.SUCCESS;

@Service
@RequiredArgsConstructor
public class ShowRoomServiceImpl implements ShowRoomService {
    private  final ShowRoomRepository showRoomRepository;
    @Override
    public ResponseBody<Object> getShowRoomByBrandId(Integer brandId, Integer page, Integer size) {
        var mapper = new ObjectMapper();
        var json = mapper.createObjectNode();
        Pageable pageable = PageRequest.of(page, size);
        Page<ShowRoomModel> showRoomModels ;
        if(Objects.isNull(brandId)){
            showRoomModels = showRoomRepository.findAll(pageable);
        }else{
          showRoomModels = showRoomRepository.findByBrandId(brandId,pageable);
        }

        json.putPOJO("page_number", page);
        json.putPOJO("total_records", showRoomModels.getTotalElements());
        json.putPOJO("page_size", size);
        json.putPOJO("data", showRoomModels.getContent());
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, json);
        return response;
    }
}
