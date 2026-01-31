package com.minedesso.backendapi.minecraftplayer.domain.dtos.in;

import lombok.Data;

import java.util.UUID;

@Data
public class MinecraftPlayerSaveCommand {
    private UUID uuid;
    private String name;
    private boolean online;
}
