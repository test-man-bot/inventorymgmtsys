package com.ims.inventorymgmtsys.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        logger.info("Authenticated user roles: " + authentication.getAuthorities());
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        String targetUrl = isAdmin ? "/admin/management" : "/catalog/list";
        logger.info("Authentication successful. Redirecting to: " + targetUrl);
        response.sendRedirect(targetUrl);
    }
}
