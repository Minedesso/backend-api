package com.minedesso.backendapi.minecraftplayer.domain.dtos.out;

import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MinecraftPlayer {
    private UUID uuid;
    private String name;
    private LocalDateTime firstLoginDate;
    private LocalDateTime lastLoginDate;
    private boolean online;

    public MinecraftPlayer(MinecraftPlayerEntity entity) {
        this.uuid = entity.getUuid();
        this.name = entity.getName();
        this.firstLoginDate = entity.getFirstLoginDate();
        this.lastLoginDate = entity.getLastLoginDate();
        this.online = entity.isOnline();
    }
}
