package quanli.duan.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ErrorData;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.authen.RefreshTokenRequest;
import quanli.duan.dto.request.authen.SignInRequest;
import quanli.duan.dto.request.authen.SignUpUserRequest;
import quanli.duan.dto.response.authen.JwtAuthenticationResponse;
import quanli.duan.entity.UsersModel;
import quanli.duan.exception.ServiceSecurityException;
import quanli.duan.repository.UsersRepository;
import quanli.duan.service.AuthenticationService;
import quanli.duan.service.JWTService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import static quanli.duan.core.response.ResponseStatus.*;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    public ResponseBody<Object> registerUser(SignUpUserRequest signUpUserRequest) {
        UsersModel usersModel = new UsersModel();
        var existsEmail = usersRepository.existsByEmail(signUpUserRequest.getEmail());

        if (existsEmail) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(EMAIL_EXIST.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, EMAIL_EXIST, errorMapping);
        }

        usersModel.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
        usersModel.setEmail(signUpUserRequest.getEmail());
        usersModel.setFirstName(signUpUserRequest.getFirstName());
        usersModel.setLastName(signUpUserRequest.getLastName());
        usersModel.setRoleId(signUpUserRequest.getRoleId());
        usersModel.setPassword(passwordEncoder.encode(signUpUserRequest.getPassword()));
        usersModel.setCreateDate(LocalDateTime.now());
        usersRepository.save(usersModel);
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, usersModel);
        return response;
    }

    public ResponseBody<Object> signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = usersRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> {
            var errorMapping = ErrorData.builder()
                    .errorKey2(INVALID_REQUEST_PARAMETER.getCode())
                    .build();
            return new ServiceSecurityException(HttpStatus.OK, INVALID_REQUEST_PARAMETER, errorMapping);
        });
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        jwtAuthenticationResponse.setUserId(user.getUserId());

        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, jwtAuthenticationResponse);
        return response;
    }

    public ResponseBody<Object> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        UsersModel usersModel = usersRepository.findByEmail(userEmail).orElseThrow();

        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), usersModel)) {
            var errorMapping = ErrorData.builder()
                    .errorKey1(INVALID_REQUEST_PARAMETER.getCode())
                    .build();
            throw new ServiceSecurityException(HttpStatus.OK, INVALID_REQUEST_PARAMETER, errorMapping);
        }
        var jwt = jwtService.generateToken(usersModel);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
        jwtAuthenticationResponse.setUserId(usersModel.getUserId());

        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, jwtAuthenticationResponse);
        return response;
    }
}
