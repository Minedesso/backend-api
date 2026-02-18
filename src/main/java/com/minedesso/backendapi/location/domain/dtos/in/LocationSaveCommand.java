package com.minedesso.backendapi.location.domain.dtos.in;

import lombok.Data;

@Data
public class LocationSaveCommand {
    private String name;
    private String item;
    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public String getName() {
        return this.name.toLowerCase();
    }
}
