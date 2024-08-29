package com.ims.inventorymgmtsys.config;

import com.ims.inventorymgmtsys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private LoginUserDetailService loginUserDetailService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    private final PasswordEncoder passwordEncoder;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private final AuditlogService auditlogService;
    private final UserService userService;

    @Autowired
    public SecurityConfig(AuditlogService auditlogService, UserService userService, PasswordEncoder passwordEncoder, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.auditlogService = auditlogService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                    .requestMatchers("/fragments/**","/js/**","/css/**","/images/**","/favicon.ico").permitAll()
                    .requestMatchers("/login", "/register","/forget","/password","/changePasswordNoLogin").permitAll()
                    .requestMatchers("/catalog/**", "/order/**", "/cart/**","/user/**","/challenge/**","/enable-2fa/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/admin/**","/sales/**","/system/**","/api/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/challenge/totp").access(new MfaAuthorizationManager())
                    .requestMatchers("/h2-console/**").permitAll()  // H2コンソールへのアクセスを許可
                    .anyRequest().authenticated()
                .and()
                .securityContext(securityContext -> securityContext.requireExplicitSave(false))
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(customAuthenticationSuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
                .and()
                .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService)
                .and()
                    .successHandler(customAuthenticationSuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
                .and()
                .csrf()
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))// H2コンソールのCSRFを無視
                    .ignoringRequestMatchers("/api/**")
                .and()
                    .httpBasic()
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
    public MfaAuthenticationCodeVerifier mfaFactorAuthenticationCodeVerifier() {
        return new TotpAuthenticationCodeVerifier();
    }
}
