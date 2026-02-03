package com.minedesso.backendapi.home.persistence;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "HOME")
public class HomeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public HomeEntity(HomeSaveCommand command) {
        this.name = command.getName();
        this.worldName = command.getWorldName();
        this.x = command.getX();
        this.y = command.getY();
        this.z = command.getZ();
        this.yaw = command.getYaw();
        this.pitch = command.getPitch();
    }

}
