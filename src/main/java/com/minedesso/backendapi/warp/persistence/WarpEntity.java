package com.minedesso.backendapi.warp.persistence;

import com.minedesso.backendapi.warp.domain.dtos.in.WarpSaveCommand;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "WARP")
public class WarpEntity {

    @Id
    private String name;
    private String permission;

    private String worldName;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public WarpEntity(WarpSaveCommand command) {
        this.name = command.getName();
        this.permission = command.getPermission();
        this.worldName = command.getWorldName();
        this.x = command.getX();
        this.y = command.getY();
        this.z = command.getZ();
        this.yaw = command.getYaw();
        this.pitch = command.getPitch();
    }

}
