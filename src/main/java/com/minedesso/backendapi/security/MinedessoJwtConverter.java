package com.minedesso.backendapi.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MinedessoJwtConverter implements Converter<Jwt, MinedessoJwtToken> {
    @Override
    public MinedessoJwtToken convert(Jwt source) {
        String firstname = source.getClaimAsString("given_name");
        String lastname = source.getClaimAsString("family_name");
        String email = source.getClaimAsString("email");
        List<GrantedAuthority> authorities = this.getAuthorities(source);
        return new MinedessoJwtToken(firstname, lastname, email, authorities, source);
    }

    private List<GrantedAuthority> getAuthorities(Jwt source) {
        Map<String, Object> resourceAccess = source.getClaimAsMap("resource_access");
        if (resourceAccess == null) {
            return List.of();
        }

        Map<String, Object> minedesso = (Map<String, Object>) resourceAccess.get("minedesso");
        if (minedesso == null) {
            return List.of();
        }

        List<String> roles = (List<String>) minedesso.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }
}
