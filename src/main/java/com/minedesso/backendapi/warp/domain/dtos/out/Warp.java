package com.minedesso.backendapi.warp.domain.dtos.out;

import com.minedesso.backendapi.warp.persistence.WarpEntity;
import lombok.Data;

@Data
public class Warp {
    private final String name;
    private final String permission;

    private final String worldName;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public Warp(WarpEntity entity) {
        this.name = entity.getName();
        this.permission = entity.getPermission();
        this.worldName = entity.getWorldName();
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
        this.yaw = entity.getYaw();
        this.pitch = entity.getPitch();
    }
}
