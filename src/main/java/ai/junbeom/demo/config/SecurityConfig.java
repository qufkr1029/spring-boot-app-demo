package ai.junbeom.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/login2", "/css/**", "/js/**", "/assets/**", "/*.ico", "/*.svg", "/*.html", "/react/**").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (API 서버로 사용 시)
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .formLogin(formLogin -> formLogin // 폼 로그인 설정
                .loginPage("/login") // 커스텀 로그인 페이지
                .permitAll()
            );

        return http.build();
    }

    // PasswordEncoder 빈 추가
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
