package com.ims.inventorymgmtsys.config;

import com.ims.inventorymgmtsys.entity.User;

public interface MfaAuthenticationCodeVerifier {
    boolean verify(User user, String code);
}
