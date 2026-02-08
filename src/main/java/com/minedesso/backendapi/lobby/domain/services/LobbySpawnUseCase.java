package com.minedesso.backendapi.lobby.domain.services;

import com.minedesso.backendapi.lobby.domain.dtos.in.LobbySpawnSaveCommand;
import com.minedesso.backendapi.lobby.domain.dtos.out.LobbySpawn;
import com.minedesso.backendapi.lobby.domain.utils.exceptions.LobbySpawnNotFoundException;

public interface LobbySpawnUseCase {
    void save(LobbySpawnSaveCommand command) throws LobbySpawnNotFoundException;

    LobbySpawn getLobbySpawn() throws LobbySpawnNotFoundException;
}
