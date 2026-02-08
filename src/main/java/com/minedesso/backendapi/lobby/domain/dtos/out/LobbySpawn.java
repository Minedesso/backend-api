package com.minedesso.backendapi.lobby.domain.dtos.out;

import com.minedesso.backendapi.lobby.persistence.LobbySpawnEntity;
import lombok.Data;

@Data
public class LobbySpawn {
    private String world;
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;

    public LobbySpawn(LobbySpawnEntity entity) {
        this.world = entity.getWorld();
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
        this.yaw = entity.getYaw();
        this.pitch = entity.getPitch();
    }
}
