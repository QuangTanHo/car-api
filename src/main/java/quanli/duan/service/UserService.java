package quanli.duan.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import quanli.duan.core.response.ResponseBody;
import quanli.duan.dto.request.users.UpdateUserRequest;
import quanli.duan.dto.request.users.UserSearchRequest;

public interface UserService {

    UserDetailsService userDetailsService();

    ResponseBody<Object> getAllUserDetail();

    ResponseBody<Object> getUserIdDetail(String userId);

    ResponseBody<Object> updateUser(UpdateUserRequest request);

    ResponseBody<Object> deleteUserById(String userId);

    ResponseBody<Object> getAllUserPage(UserSearchRequest request);
}
