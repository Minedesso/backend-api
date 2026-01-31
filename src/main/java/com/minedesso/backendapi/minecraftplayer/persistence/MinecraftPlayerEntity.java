package com.minedesso.backendapi.minecraftplayer.persistence;

import com.minedesso.backendapi.minecraftplayer.domain.dtos.in.MinecraftPlayerSaveCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "MINECRAFT_PLAYER")
public class MinecraftPlayerEntity {
    @Id
    private UUID uuid;

    private String name;
    private LocalDateTime firstLoginDate;
    private LocalDateTime lastLoginDate;
    private boolean online;

    public MinecraftPlayerEntity(MinecraftPlayerSaveCommand command) {
        this.setAttributes(command);
        this.firstLoginDate = LocalDateTime.now();
    }

    public void update(MinecraftPlayerSaveCommand command) {
        this.setAttributes(command);
    }

    private void setAttributes(MinecraftPlayerSaveCommand command) {
        this.uuid = command.getUuid();
        this.name = command.getName();
        this.lastLoginDate = LocalDateTime.now();
        this.online = command.isOnline();
    }
}
