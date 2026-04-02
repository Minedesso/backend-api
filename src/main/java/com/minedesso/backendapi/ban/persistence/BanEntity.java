package com.minedesso.backendapi.ban.persistence;

import com.minedesso.backendapi.ban.domain.dtos.in.BanSaveCommand;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ban")
public class BanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean isActive;

    @ManyToOne()
    @JoinColumn(name = "reason_id")
    private ReasonEntity reason;

    @ManyToOne()
    @JoinColumn(name = "banned_by_uuid")
    private MinecraftPlayerEntity bannedBy;

    public BanEntity(BanSaveCommand command, ReasonEntity reason) {
        this.createdAt = command.getBannedAt();
        this.expiresAt = calculateExpiresAt(command.getBannedAt(), command.getDuration());
        this.isActive = true;
        this.reason = reason;
    }

    /**
     * Calculates the expiration date based on the bannedAt date and the duration string.
     * @param bannedAt - the date and time when the ban was issued
     * @param duration - the duration of the ban, which can be in formats like "1d" for 1 day, "2h" for 2 hours, etc.
     *                 matches following regex: "\\d+[smhdwy]"
     * @return the calculated expiration date and time
     */
    private LocalDateTime calculateExpiresAt(LocalDateTime bannedAt, String duration) throws IllegalArgumentException {
        if (duration.equalsIgnoreCase("perm")) {
            return null; // Represents a permanent ban
        }

        char timeUnit = duration.charAt(duration.length() - 1);
        int timeValue = Integer.parseInt(duration.substring(0, duration.length() - 1));

        return switch (timeUnit) {
            case 's' -> bannedAt.plusSeconds(timeValue);
            case 'm' -> bannedAt.plusMinutes(timeValue);
            case 'h' -> bannedAt.plusHours(timeValue);
            case 'd' -> bannedAt.plusDays(timeValue);
            case 'w' -> bannedAt.plusWeeks(timeValue);
            case 'y' -> bannedAt.plusYears(timeValue);
            default -> throw new IllegalArgumentException("Invalid duration format: " + duration);
        };
    }

}
