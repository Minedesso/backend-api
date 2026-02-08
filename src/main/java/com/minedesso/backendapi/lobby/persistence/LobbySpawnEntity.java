package com.minedesso.backendapi.lobby.persistence;

import com.minedesso.backendapi.lobby.domain.dtos.in.LobbySpawnSaveCommand;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "LOBBY_SPAWN")
public class LobbySpawnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String world;
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;

    public LobbySpawnEntity(LobbySpawnSaveCommand command) {
        this.setAttributes(command);
    }

    public void update(LobbySpawnSaveCommand command) {
        this.setAttributes(command);
    }

    private void setAttributes(LobbySpawnSaveCommand command) {
        this.world = command.getWorld();
        this.x = command.getX();
        this.y = command.getY();
        this.z = command.getZ();
        this.yaw = command.getYaw();
        this.pitch = command.getPitch();
    }
}
