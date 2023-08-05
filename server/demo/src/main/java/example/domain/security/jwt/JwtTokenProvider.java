package example.domain.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private final Key key;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Token 생성
    public AccessTokenDto generateTokenDtoSet(Authentication authentication) {
        // 권한을 받음. authorities = ROLE_USER
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // 현재 시간
        long now = new Date().getTime();

        // Token 만료 시간
        Date accessTokenExpiresIn = new Date(now + ExpireTime.ACCESS_TOKEN_EXPIRE_TIME.getTime());
        Date refreshTokenExpiresIn = new Date(now + ExpireTime.REFRESH_COOKIE_EXPIRE_TIME.getTime());
        log.info(String.valueOf(accessTokenExpiresIn));
        log.info(String.valueOf(refreshTokenExpiresIn));

        // Token 생성
        log.info("name : {}", authentication.getName());
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,
                        authorities)
                // Custom Claim 지정, Claims는 JWT의 body이고 JWT 생성자가 JWT를 받는이들이게 제시하기 바라는 정보를 포함
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(accessTokenExpiresIn) // 만료시간
                .signWith(key, SignatureAlgorithm.HS512) // sign key 지정
                .compact();

        // TokenDto에 생성한 Token의 정보를 넣는다
        return AccessTokenDto.builder()
                .grantType(BEARER_TYPE)
                .token(accessToken)
                .tokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

    // Token을 받았을 때 Token의 인증을 꺼내는 메소드
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        String username = getUserName(accessToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Token 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public String getUserName(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts
                    .parserBuilder() // JwtParseBuilder 인스턴스 생성
                    .setSigningKey(key) // JWT 서명 검증을 위한 키 설정
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
