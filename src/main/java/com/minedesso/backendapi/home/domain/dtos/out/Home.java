package com.minedesso.backendapi.home.domain.dtos.out;

import com.minedesso.backendapi.home.persistence.HomeEntity;
import lombok.Data;

@Data
public class Home {
    private String name;

    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public Home(HomeEntity homeEntity) {
        this.name = homeEntity.getName();
        this.worldName = homeEntity.getWorldName();
        this.x = homeEntity.getX();
        this.y = homeEntity.getY();
        this.z = homeEntity.getZ();
        this.yaw = homeEntity.getYaw();
        this.pitch = homeEntity.getPitch();
    }

}
