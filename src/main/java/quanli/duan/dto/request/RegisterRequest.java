package quanli.duan.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String password;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    private String state;

    private String city;

    @JsonProperty("zip_code")
    private String zipCode;

    @JsonProperty("user_name_role")
    private String userNameRole;
}
