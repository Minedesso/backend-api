package com.minedesso.backendapi.lobby.domain.utils.exceptions;

public class LobbySpawnNotFoundException extends Exception {
    public LobbySpawnNotFoundException() {
        super("Lobby spawn not found");
    }
}
