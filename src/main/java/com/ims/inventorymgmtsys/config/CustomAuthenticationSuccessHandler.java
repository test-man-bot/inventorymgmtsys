package com.ims.inventorymgmtsys.config;

import com.ims.inventorymgmtsys.entity.Auditlog;
import com.ims.inventorymgmtsys.service.AuditlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    private final AuditlogService auditlogService;

    public CustomAuthenticationSuccessHandler(AuditlogService auditlogService) {
        this.auditlogService = auditlogService;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        logger.info("Authenticated user roles: " + authentication.getAuthorities());
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
