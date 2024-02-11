package com.example.agregator.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADM;

    @Override
    public String getAuthority() {
        return name();
    }
}
