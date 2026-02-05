package com.minedesso.backendapi.home.domain.dtos.in;

import lombok.Data;

import java.util.UUID;

@Data
public class HomeSaveCommand {
    private UUID ownerUuid;
    private String name;
    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
}
