package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quanli.duan.service.BrandService;
import quanli.duan.service.VersionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1/brand")
public class BrandController {
    final BrandService brandService;


    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllBranch() {
        return ResponseEntity.ok(brandService.getAllBranch());
    }

    @GetMapping("/get-menu")
    public ResponseEntity<Object> getMenu() {
        return ResponseEntity.ok(brandService.getMenu());
    }

}
