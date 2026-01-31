package com.minedesso.backendapi.minecraftplayer.domain.services;

import com.minedesso.backendapi.minecraftplayer.domain.dtos.in.MinecraftPlayerSaveCommand;
import com.minedesso.backendapi.minecraftplayer.domain.dtos.out.MinecraftPlayer;
import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;

import java.util.List;
import java.util.UUID;

public interface MinecraftPlayerUseCase {
    void save(MinecraftPlayerSaveCommand command);

    List<MinecraftPlayer> getAll();

    MinecraftPlayer getById(UUID uuid) throws MinecraftPlayerNotFoundException;

    void delete(UUID uuid) throws MinecraftPlayerNotFoundException;
}
