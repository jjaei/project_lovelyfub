package example.domain.config;

import example.domain.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .cors()
                .and()
                .sessionManagement()  // 세션 정책 설정
                .and()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .oauth2Login() // OAuth 2 로그인 설정 진입점
                .successHandler(new UserLoginSuccessHandler())
                .defaultSuccessUrl("http://localhost:3000/main", true)
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
        return http.build();

    }
    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
//            cors 오류를 해결하기 위해 Controller 에 @CrossOrigin 을 붙여주는 방법도 있지만
//				이 방식은 필터 추가와 다르게 인증이 필요 없는 url 만 처리해줌
                    .addFilter(corsConfig.corsFilter()); // cors 에 대해 허락하는 필터
        }
    }
}
