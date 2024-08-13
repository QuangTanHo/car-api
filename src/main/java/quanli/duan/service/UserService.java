package quanli.duan.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.UserRequest;
import quanli.duan.entity.UserModel;
import quanli.duan.repository.UserRepository;

import static quanli.duan.core.response.ResponseStatus.SUCCESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserModel saveUser(UserRequest request) {
//        UserModel userModel = UserModel.builder().build();
        return null;
    }

    public ResponseBody<Object> getUserDetail(String userId) {
        var response = new ResponseBody<>();
        response.setOperationSuccess(SUCCESS, "mock");
        return response;
    }
}
