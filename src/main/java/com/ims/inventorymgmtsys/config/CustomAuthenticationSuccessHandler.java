package com.ims.inventorymgmtsys.config;

import com.ims.inventorymgmtsys.entity.Auditlog;
import com.ims.inventorymgmtsys.entity.User;
import com.ims.inventorymgmtsys.repository.AuthorityRepository;
import com.ims.inventorymgmtsys.service.AuditlogService;
import com.ims.inventorymgmtsys.service.UserService;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    private final AuthorityRepository authorityRepository;

    @Value("${mfa.redirect.url:/challenge/totp}")
    private String mfaRedirectUrl;

    private final AuditlogService auditlogService;
    public CustomAuthenticationSuccessHandler(AuditlogService auditlogService, UserService userService, AuthorityRepository authorityRepository) {
        this.auditlogService = auditlogService;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("Authenticated user roles: " + authentication.getAuthorities());

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = customUserDetails.getUser();
        List<GrantedAuthority> updateAuthorities = authorityRepository.getRole(user.getUserName()).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), updateAuthorities);
//        if (authentication instanceof MfaAuthentication) {
//            MfaAuthentication mfaAuth = (MfaAuthentication) authentication;
            // Debugging logs for MFA status
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        logger.info("Updated authenticated user roles: " + newAuth.getAuthorities());
        logger.info("MFA enabled: " + user.getMfaEnabled());
//            logger.info("MFA completed: " + mfaAuth.isMfaCompleted());


            if (user.getMfaEnabled()) {
                logger.info("MFA is required. Redirecting to MFA page");
                auditlogService.save(createAuditlog(authentication.getName(), "MFA_REQUIRED", "MFA challenge triggered"));
                response.sendRedirect(mfaRedirectUrl);
                return;
            }
//        }
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        String targetUrl = isAdmin ? "/admin/management" : "/catalog/list";
        logger.info("Authentication successful. Redirecting to: " + targetUrl);
        auditlogService.save(createAuditlog(authentication.getName(), "LOGIN_SUCCESS", "User logged in Successfully"));
        response.sendRedirect(targetUrl);
    }

    private Auditlog createAuditlog(String username, String eventType, String details) {
        Auditlog auditlog = new Auditlog();
        auditlog.setUsername(username);
        auditlog.setEventType(eventType);
        auditlog.setDetails(details);
        auditlog.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return auditlog;
    }

}
