package quanli.duan.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import quanli.duan.entity.UserModel;
import quanli.duan.entity.UserRoleModel;
import quanli.duan.repository.UserRepository;
import quanli.duan.repository.UserRoleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

    final UserRepository userRepository;
    final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        List<UserRoleModel> userRoleModel = userRoleRepository.findAllByUserId(user.getUserId());

        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(userRoleModel));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<UserRoleModel> userRoleModels) {
        return userRoleModels.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}
