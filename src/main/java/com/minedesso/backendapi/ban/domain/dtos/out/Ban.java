package com.minedesso.backendapi.ban.domain.dtos.out;

import com.minedesso.backendapi.ban.persistence.BanEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Ban {

    private LocalDateTime bannedAt;
    private LocalDateTime expiresAt;

    private String reason;
    private String bannedBy;

    public Ban(BanEntity banEntity) {
        this.bannedAt = banEntity.getCreatedAt();
        this.expiresAt = banEntity.getExpiresAt();
        this.reason = banEntity.getReason().getReason();
        this.bannedBy = banEntity.getBannedBy().getName();
    }

}
