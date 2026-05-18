package com.minedesso.backendapi.home.domain.utils.exceptions;

public class ActiveBanNotFoundException extends Exception {
    public ActiveBanNotFoundException() {
        super("No active ban found for the player.");
    }
}
