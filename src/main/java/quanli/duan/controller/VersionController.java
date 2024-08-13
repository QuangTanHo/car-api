package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quanli.duan.service.VersionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1/version")
public class VersionController {
    final VersionService versionService;

    @GetMapping("/get-by-brandId")
    public ResponseEntity<Object> getAllVersionByBrandId(@RequestParam(value = "brandId", required = false)  Integer brandId) {
        return ResponseEntity.ok(versionService.getAllVersionByBrandId(brandId));
    }
}
