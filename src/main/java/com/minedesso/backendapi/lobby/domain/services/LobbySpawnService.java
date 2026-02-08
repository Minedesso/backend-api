package com.minedesso.backendapi.lobby.domain.services;

import com.minedesso.backendapi.lobby.domain.dtos.in.LobbySpawnSaveCommand;
import com.minedesso.backendapi.lobby.domain.dtos.out.LobbySpawn;
import com.minedesso.backendapi.lobby.domain.utils.exceptions.LobbySpawnNotFoundException;
import com.minedesso.backendapi.lobby.persistence.LobbySpawnEntity;
import com.minedesso.backendapi.lobby.persistence.LobbySpawnRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LobbySpawnService implements LobbySpawnUseCase {
    private final LobbySpawnRepository repository;

    @Override
    public void save(LobbySpawnSaveCommand command) throws LobbySpawnNotFoundException {
        boolean lobbySpawnExists = this.repository.count() > 0;
        LobbySpawnEntity lobbySpawnEntity;
        if (lobbySpawnExists) {
            lobbySpawnEntity = this.findLobbySpawn();
            lobbySpawnEntity.update(command);
        } else {
            lobbySpawnEntity = new LobbySpawnEntity(command);
        }
        this.repository.save(lobbySpawnEntity);

    }

    @Override
    public LobbySpawn getLobbySpawn() throws LobbySpawnNotFoundException {
        return new LobbySpawn(this.findLobbySpawn());
    }

    private LobbySpawnEntity findLobbySpawn() throws LobbySpawnNotFoundException {
        return this.repository.findAll().stream()
                .findFirst()
                .orElseThrow(LobbySpawnNotFoundException::new);
    }
}
