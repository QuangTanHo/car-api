package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quanli.duan.service.ShowRoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class ShowRoomController {
    private final ShowRoomService showRoomService;

    @GetMapping("un-auth/showroom/get-by-brandId")
    public ResponseEntity<Object> getAllVersionByBrandId(@RequestParam(value = "brandId", required = false )  Integer brandId ,
                                                         @RequestParam(value = "page", required = false )  Integer page,
                                                         @RequestParam(value = "size", required = false )  Integer size) {
        return ResponseEntity.ok(showRoomService.getShowRoomByBrandId(brandId, page, size));
    }

    @GetMapping("un-auth/showroom/get-by-outstanding")
    public ResponseEntity<Object> getShowRoomByOutstanding(  @RequestParam(value = "page", required = false )  Integer page,
                                                             @RequestParam(value = "size", required = false )  Integer size) {
        return ResponseEntity.ok(showRoomService.getShowRoomByOutstanding(page,size));
    }
}
