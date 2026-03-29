package com.minedesso.backendapi.minecraftplugin.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "MINECRAFT_PLUGIN")
public class MinecraftPluginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String apiKey;

    public MinecraftPluginEntity(String name, String apiKey) {
        this.name = name;
        this.apiKey = apiKey;
    }

    public void update(String name) {
        this.name = name;
    }
}
