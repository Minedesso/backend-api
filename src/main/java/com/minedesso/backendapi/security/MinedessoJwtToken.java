package com.minedesso.backendapi.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

@Getter
public class MinedessoJwtToken extends JwtAuthenticationToken {
    private final String firstname;
    private final String lastname;
    private final String email;

    public MinedessoJwtToken(String firstname,
                             String lastname,
                             String email,
                             List<GrantedAuthority> authorities,
                             Jwt jwt) {
        super(jwt, authorities);
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
