package com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions;

import java.util.UUID;

public class MinecraftPlayerNotFoundException extends Exception {
    public MinecraftPlayerNotFoundException(UUID uuid) {
        super("minecraft player with uuid " + uuid.toString() + " not found!");
    }
}
