package quanli.duan.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quanli.duan.dto.request.UserRequest;
import quanli.duan.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Object> saveUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.saveUser(request));
    }

    @GetMapping("/user/detail/{user_id}")
    public ResponseEntity<Object> getUserDetail(@PathVariable("user_id") String userId) {
        return ResponseEntity.ok(userService.getUserDetail(userId));
    }
}
