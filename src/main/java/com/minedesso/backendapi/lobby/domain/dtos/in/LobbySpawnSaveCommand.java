package com.minedesso.backendapi.lobby.domain.dtos.in;

import lombok.Data;

@Data
public class LobbySpawnSaveCommand {
    private String world;
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;
}
