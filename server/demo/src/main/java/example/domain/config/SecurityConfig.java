package example.domain.config;

import example.domain.user.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig{
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login() // OAuth 2 로그인 설정 진입점
                .userInfoEndpoint() // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정
                .userService(customOAuth2UserService);

        return http.build();

    }
}
