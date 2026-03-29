package com.minedesso.backendapi.security;

import com.minedesso.backendapi.minecraftplugin.persistence.MinecraftPluginEntity;
import com.minedesso.backendapi.minecraftplugin.persistence.MinecraftPluginRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {
    private final MinecraftPluginRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String name = request.getHeader("NAME");
        String apiKey = request.getHeader("API-KEY");
        if (name == null || apiKey == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Optional<MinecraftPluginEntity> minecraftPluginOpt = this.repository.findByName(name);
        if (minecraftPluginOpt.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        MinecraftPluginEntity minecraftPlugin = minecraftPluginOpt.get();
        if (!this.passwordEncoder.matches(apiKey, minecraftPlugin.getApiKey())) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(name, apiKey, List.of(new SimpleGrantedAuthority("ROLE_PLUGIN")));
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);
    }
}
