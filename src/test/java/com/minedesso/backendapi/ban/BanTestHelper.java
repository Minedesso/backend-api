package com.minedesso.backendapi.ban;

import com.minedesso.backendapi.ban.domain.dtos.in.BanSaveCommand;

import java.time.LocalDateTime;
import java.util.UUID;

public class BanTestHelper {
    public static BanSaveCommand createBanSaveCommand() {
        return BanSaveCommand.builder()
                .reasonId(1)
                .duration("1d")
                .bannedAt(LocalDateTime.now())
                .targetName("Alessio")
                .bannedBy(UUID.fromString("a22e9e92-1894-4d63-993c-a09f0e1edc6f"))
                .build();
    }

    public static BanSaveCommand createBanSaveCommandUnvalidReasonId() {
        return BanSaveCommand.builder()
                .reasonId(10000)
                .duration("1d")
                .bannedAt(LocalDateTime.now())
                .targetName("Alessio")
                .bannedBy(UUID.fromString("a22e9e92-1894-4d63-993c-a09f0e1edc6f"))
                .build();
    }

    public static BanSaveCommand createBanSaveCommandUnvalidTarget() {
        return BanSaveCommand.builder()
                .reasonId(1)
                .duration("1d")
                .bannedAt(LocalDateTime.now())
                .targetName(".")
                .bannedBy(UUID.fromString("a22e9e92-1894-4d63-993c-a09f0e1edc6f"))
                .build();
    }
}
