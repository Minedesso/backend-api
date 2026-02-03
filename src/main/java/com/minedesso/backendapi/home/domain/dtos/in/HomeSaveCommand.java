package com.minedesso.backendapi.home.domain.dtos.in;

import java.util.UUID;

public record HomeSaveCommand(
        UUID ownerUuid,
        String name,
        String worldName,
        double x,
        double y,
        double z,
        float yaw,
        float pitch
) {}
