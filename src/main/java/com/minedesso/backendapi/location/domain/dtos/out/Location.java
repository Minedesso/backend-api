package com.minedesso.backendapi.location.domain.dtos.out;

import com.minedesso.backendapi.location.persistence.LocationEntity;
import lombok.Data;

@Data
public class Location {
    private String name;
    private String item;
    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public Location(LocationEntity entity) {
        this.name = entity.getName();
        this.item = entity.getItem();
        this.worldName = entity.getWorldName();
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
        this.yaw = entity.getYaw();
        this.pitch = entity.getPitch();
    }
}
