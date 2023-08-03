package example.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.awt.SystemColor.control;

@Configuration // 스프링 빈으로 등록
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해
        registry.addMapping("/**")
                // Origin이 http:localhost:3000에 대해
                .allowedOrigins("http://localhost:3000", "http://ec2-3-39-210-13.ap-northeast-2.compute.amazonaws.com:8080")
                .allowedMethods("OPTIONS", "GET", "POST",  "DELETE")
                .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept")
                .exposedHeaders("Authorization")
                .allowCredentials(true);
    }
}
