package example.domain.security.jwt;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccessTokenDto {
    private String grantType;
    private String token;
    private Long tokenExpiresIn;
}
