package com.ims.inventorymgmtsys.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oAuth2User;

    public CustomOAuth2User(Collection<SimpleGrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
        this.oAuth2User = new DefaultOAuth2User(authorities, attributes, nameAttributeKey);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SimpleGrantedAuthority に変換する
        return oAuth2User.getAuthorities().stream()
                .map(auth -> (SimpleGrantedAuthority) auth)
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return oAuth2User.getName();
    }
}
