package com.minedesso.backendapi.ban.domain.dtos.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BanSaveCommand {

    private long reasonId;
    private String duration;
    private LocalDateTime bannedAt;

    private String targetName;
    private UUID bannedBy;

}
