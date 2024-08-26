package quanli.duan.controller;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quanli.duan.dto.request.UserRequest;
import quanli.duan.dto.request.users.UpdateUserRequest;
import quanli.duan.dto.request.users.UserSearchRequest;
import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final Validator validator;

    @GetMapping("/un-auth/user/user-list")
    public ResponseEntity<Object> getAllUserDetail() {
        return ResponseEntity.ok(userService.getAllUserDetail());
    }

    @GetMapping("/un-auth/user/{user_id}")
    public ResponseEntity<Object> getUserDetail(@PathVariable("user_id") String userId) {
        return ResponseEntity.ok(userService.getUserIdDetail(userId));
    }

    @PostMapping("/un-auth/user/user-update")
    public ResponseEntity<Object> updateUserDetail(@RequestBody UpdateUserRequest request) {
        this.validateRequest(request);
        return ResponseEntity.ok(userService.updateUser(request));
    }

    @DeleteMapping("/admin/user/delete/{user_id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("user_id") String userId) {
        return ResponseEntity.ok(userService.deleteUserById(userId));
    }

    @PostMapping("/admin/category/get-all-user")
    public ResponseEntity<Object> getAllUser(@RequestBody UserSearchRequest request) {
        this.validateRequest(request);
        return ResponseEntity.ok(userService.getAllUserPage(request));
    }

    private <T> void validateRequest(T request) {
        var violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ServiceSecurityException(violations);
    }
}

