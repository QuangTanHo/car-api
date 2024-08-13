package quanli.duan.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import quanli.duan.dto.request.RegisterRequest;
import quanli.duan.repository.UserRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = UserRepository.TABLE)
public class UserModel {

    @Id
    @Column(nullable = false)
    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    public static UserModel of(RegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        return UserModel.builder()
                .userId(UUID.randomUUID().toString().replaceAll("-",""))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .state(registerRequest.getState())
                .phoneNumber(registerRequest.getPhoneNumber())
                .address(registerRequest.getAddress())
                .city(registerRequest.getCity())
                .zipCode(registerRequest.getZipCode())
                .build();
    }
}
