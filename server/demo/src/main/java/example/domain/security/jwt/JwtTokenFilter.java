package example.domain.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;

    // Request Header에서 토큰 정보를 꺼내옴
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        // StringUtils.hasText(arg) : 파라미터가 문자열인지 확인
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7); // Ex) Bearer xxxxx~~ -> xxxxx~~ (7번째 문자열부터 리턴)
        }
        return null;
    }

    /*
    필터링을 실행하는 메소드
    resolveToken 메소드를 통해 토큰 정보를 꺼내오고, validateToken 메소드로 토큰의 유효성 검사 ->
    유효하다면 Authentication을 가져와서 SecurityContext에 저장 ->
    SecurityContext에서 허가된 uri 이외의 모든 Request 요청은 이 필터를 거치며, 토큰 정보가 없거나 유효하지 않으면 정상적으로 수행되지 않음.
    반대로 Request가 정상적으로 Controller까지 도착했으면, SecurityContext에 Member ID가 존재한다는 것이 보장
    */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = resolveToken(request);

        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            // Authentication -> SecurityContext -> SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response); // FilterChain으로 연결하여 준다.
    }
}