package com.ims.inventorymgmtsys.config;

import com.ims.inventorymgmtsys.service.CustomOAuth2UserService;
import com.ims.inventorymgmtsys.service.LoginUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginUserDetailService loginUserDetailService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                    .requestMatchers("/fragments/**","/js/**","/css/**").permitAll()
                    .requestMatchers("/login", "/register").permitAll()
                    .requestMatchers("/catalog/**", "/order/**", "/cart/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/h2-console/**").permitAll()  // H2コンソールへのアクセスを許可
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
//                    .defaultSuccessUrl("/catalog/list", true)
                    .successHandler(authenticationSuccessHandler())
                    .failureUrl("/login?failure")
                .and()
                .oauth2Login()
                    .loginPage("/login")
                    .failureUrl("/login?failure")
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                .and()
                    .successHandler(authenticationSuccessHandler())
                .and()
                .csrf()
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))  // H2コンソールのCSRFを無視
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/access-denied")
                .and()
                .headers()
                    .frameOptions().sameOrigin()  // フレーム内でH2コンソールを表示できるように設定
                .and()
                .requestCache().disable();

        http.userDetailsService(loginUserDetailService);
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
