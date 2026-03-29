package com.minedesso.backendapi.minecraftplugin.domain.dtos.out;

import com.minedesso.backendapi.minecraftplugin.persistence.MinecraftPluginEntity;
import lombok.Data;

@Data
public class MinecraftPlugin {
    private long id;
    private String name;
    private String apiKey;

    public MinecraftPlugin(MinecraftPluginEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.apiKey = entity.getApiKey();
    }
}
