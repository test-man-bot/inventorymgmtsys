package com.ims.inventorymgmtsys.security.utils;

import com.ims.inventorymgmtsys.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {
    private SecurityUtils() {}
    public static String getCurrentId () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUserId();
        }
        return null;
    }

}
