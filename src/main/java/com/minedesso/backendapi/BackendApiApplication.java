package com.minedesso.backendapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Minedesso API", version = "v1"),
        security = @SecurityRequirement(name = "keycloak")
)
@SecurityScheme(
        name = "keycloak",
        type = SecuritySchemeType.OPENIDCONNECT,
        openIdConnectUrl = "http://localhost:8090/realms/minedesso/.well-known/openid-configuration"
)
public class BackendApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendApiApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
