package com.github.uinet.project.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    GUEST,
    CLIENT,
    MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
