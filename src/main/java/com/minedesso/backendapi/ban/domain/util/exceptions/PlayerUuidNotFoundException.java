package com.minedesso.backendapi.ban.domain.util.exceptions;

public class PlayerUuidNotFoundException extends Exception {
    public PlayerUuidNotFoundException(String name) {
        super("UUID from player with name " + name + " not found.");
    }
}
