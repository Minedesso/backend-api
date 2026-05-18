package com.minedesso.backendapi.ban.domain.util.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:mojang-api.properties")
public class MojangApiPropertyConfig {

	@Value("${mojang-api-get-player-uuid-url}")
	private String playerUuidUrl;

}
