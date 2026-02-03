package com.minedesso.backendapi.home.domain.dtos.in;

import lombok.Data;

import java.util.UUID;

@Data
public class HomeSaveCommand {
    private final UUID ownerUuid;
    private final String name;

    private final String worldName;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
}
