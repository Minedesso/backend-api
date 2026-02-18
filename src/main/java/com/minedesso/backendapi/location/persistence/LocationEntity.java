package com.minedesso.backendapi.location.persistence;

import com.minedesso.backendapi.location.domain.dtos.in.LocationSaveCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LOCATION")
public class LocationEntity {
    @Id
    private String name;

    private String item;
    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public LocationEntity(LocationSaveCommand command) {
        this.setValues(command);
    }

    public void update(LocationSaveCommand command) {
        this.setValues(command);
    }

    private void setValues(LocationSaveCommand command) {
        this.name = command.getName();
        this.item = command.getItem();
        this.worldName = command.getWorldName();
        this.x = command.getX();
        this.y = command.getY();
        this.z = command.getZ();
        this.yaw = command.getYaw();
        this.pitch = command.getPitch();
    }
}
