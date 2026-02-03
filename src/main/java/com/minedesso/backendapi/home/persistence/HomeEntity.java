package com.minedesso.backendapi.home.persistence;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
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
        this.name = command.name();
        this.worldName = command.worldName();
        this.x = command.x();
        this.y = command.y();
        this.z = command.z();
        this.yaw = command.yaw();
        this.pitch = command.pitch();
    }

}
