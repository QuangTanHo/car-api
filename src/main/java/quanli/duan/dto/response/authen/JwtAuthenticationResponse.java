package quanli.duan.dto.response.authen;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

    private String token;
    private String refreshToken;
    private String userId;
}
