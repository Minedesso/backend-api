package com.minedesso.backendapi.warp.domain.dtos.in;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarpSaveCommand {
    private final String name;
    private String permission;

    private final String worldName;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
}
