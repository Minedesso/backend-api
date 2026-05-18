package com.minedesso.backendapi.minecraftplayer.domain.dtos.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinecraftPlayerSaveCommand {
    private UUID uuid;
    private String name;
    private boolean online;
}
