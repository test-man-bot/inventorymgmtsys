package com.ims.inventorymgmtsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers("/catalog", "/order", "/cart").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/h2-console/**").permitAll()  // H2コンソールへのアクセスを許可
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?failure")
                .defaultSuccessUrl("/catalog/list")
                .and()
                .csrf()
                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))  // H2コンソールのCSRFを無視
                .and()
                .headers()
                .frameOptions().sameOrigin();  // フレーム内でH2コンソールを表示できるように設定

        return http.build();
    }

}
