package quanli.duan.dto.request.authen;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {

    @NotBlank(message = "Email is not blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is not blank")
    private String password;
}
