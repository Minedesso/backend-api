package com.minedesso.backendapi.home;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;

import java.util.UUID;

public class HomeTestHelper {
    public static HomeSaveCommand createHomeSaveCommand() {
        return HomeSaveCommand.builder()
                .ownerUuid(UUID.fromString("a22e9e92-1894-4d63-993c-a09f0e1edc6f"))
                .name("World 1")
                .worldName("world")
                .x(0)
                .y(64)
                .z(0)
                .yaw(0.5F)
                .pitch(0.5F)
                .build();
    }
}
