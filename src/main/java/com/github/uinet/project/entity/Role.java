package com.github.uinet.project.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    GUEST,
    CLIENT,
    MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
