package com.minedesso.backendapi.minecraftplugin.domain.dtos.in;

import lombok.Data;

@Data
public class MinecraftPluginSaveCommand {
    private Long id;
    private String name;
    private String apiKey;

    public String getName() {
        return this.name.toLowerCase();
    }
}
