package quanli.duan.controller;


import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import quanli.duan.dto.request.authen.RefreshTokenRequest;
import quanli.duan.dto.request.authen.SignInRequest;
import quanli.duan.dto.request.authen.SignUpUserRequest;
import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/un-auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final Validator validator;

    @PostMapping("/signup/user")
    public ResponseEntity<Object> signUpUser(@RequestBody SignUpUserRequest request) {
        this.validateRequest(request);
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> signIn(@RequestBody SignInRequest request) {
        this.validateRequest(request);
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refresh(@RequestBody RefreshTokenRequest request) {
        this.validateRequest(request);
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    private <T> void validateRequest(T request) {
        var violations = validator.validate(request);
        if (!violations.isEmpty()) throw new ServiceSecurityException(violations);
    }
}
