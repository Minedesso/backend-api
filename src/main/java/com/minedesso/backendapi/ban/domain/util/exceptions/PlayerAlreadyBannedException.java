package com.minedesso.backendapi.ban.domain.util.exceptions;

public class PlayerAlreadyBannedException extends Exception {
    public PlayerAlreadyBannedException(String playerName) {
        super("Player " + playerName + " is already banned.");
    }
}
