package quanli.duan.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import quanli.duan.configuration.JWTGenerator;
import quanli.duan.core.response.AuthResponse;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.LoginRequest;
import quanli.duan.dto.request.RegisterRequest;
import quanli.duan.entity.UserModel;
import quanli.duan.entity.UserRoleModel;
import quanli.duan.repository.RoleRepository;
import quanli.duan.repository.UserRepository;
import quanli.duan.repository.UserRoleRepository;
import static quanli.duan.core.response.ResponseStatus.SUCCESS;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    final UserRepository userRepository;
    final UserRoleRepository userRoleRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;
    final JWTGenerator jwtGenerator;

    public ResponseBody<Object> register(RegisterRequest registerRequest) {
        var response = new ResponseBody<>();
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            response.setOperationSuccess(SUCCESS, "fail");
        }

        UserModel userModel = UserModel.of(registerRequest, passwordEncoder);
        userRepository.save(userModel);
//        var roleModel = roleRepository.findByRoleName(registerRequest.getUserNameRole());
        UserRoleModel userRoleModel = UserRoleModel.of(userModel.getUserId(), registerRequest.getUserNameRole());
        userRoleRepository.save(userRoleModel);
        
        response.setOperationSuccess(SUCCESS, "success");
        return response;
    }

    public ResponseBody<Object> login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, new AuthResponse(token));
        return response;
    }
}