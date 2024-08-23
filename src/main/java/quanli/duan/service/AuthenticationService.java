package quanli.duan.service;


import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.authen.RefreshTokenRequest;
import quanli.duan.dto.request.authen.SignInRequest;
import quanli.duan.dto.request.authen.SignUpUserRequest;

public interface AuthenticationService {

    ResponseBody<Object> registerUser(SignUpUserRequest signUpUserRequest);

    ResponseBody<Object> signIn(SignInRequest signInRequest);

    ResponseBody<Object> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
