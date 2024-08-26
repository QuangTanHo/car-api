package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quanli.duan.dto.request.brand.BrandSaveOrUpdateRequest;
import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.service.BrandService;
import jakarta.validation.Validator;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class BrandController {
    final BrandService brandService;
    private final Validator validator;

    @GetMapping("un-auth/brand/getAll")
    public ResponseEntity<Object> getAllBranch() {
        return ResponseEntity.ok(brandService.getAllBranch());
    }

    @GetMapping("un-auth/brand/get-list-brand")
    public ResponseEntity<Object> getListBranch(@Param("page") Integer page ,@Param("size") Integer size) {
        return ResponseEntity.ok(brandService.getListBranch(page,size));
    }

    @GetMapping("un-auth/brand/get-menu")
    public ResponseEntity<Object> getMenu() {
        return ResponseEntity.ok(brandService.getMenu());
    }

    @GetMapping("un-auth/brand/{brandId}")
    public ResponseEntity<Object> getByBrandId(@PathVariable("brandId") Integer brandId) {
        return ResponseEntity.ok(brandService.getBrandById(brandId));
    }
    @PostMapping("un-auth/brand/save")
    public ResponseEntity<Object> save(@RequestBody  BrandSaveOrUpdateRequest request) {
        this.validateRequest(request);
        return ResponseEntity.ok(brandService.saveBrand(request));
    }
    @PutMapping("un-auth/brand/update")
    public ResponseEntity<Object> update(@RequestBody BrandSaveOrUpdateRequest request) throws IOException {
        this.validateRequest(request);
        return ResponseEntity.ok(brandService.updateBrand(request));
    }
    @DeleteMapping("admin/brand/delete/{brand_id}")
    public ResponseEntity<Object> delete(@PathVariable("brand_id") Integer brandId) throws IOException {
        return ResponseEntity.ok(brandService.deleteBrandById(brandId));
    }


    private <T> void validateRequest(T request) {
        var violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ServiceSecurityException(violations);
    }
}
