package com.minedesso.backendapi.minecraftplayer.persistence;

import com.minedesso.backendapi.home.persistence.HomeEntity;
import com.minedesso.backendapi.balance.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.balance.persistence.BalanceEntity;
import com.minedesso.backendapi.minecraftplayer.domain.dtos.in.MinecraftPlayerSaveCommand;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "MINECRAFT_PLAYER")
public class MinecraftPlayerEntity {
    @Id
    private UUID uuid;

    private String name;
    private LocalDateTime firstLoginDate;
    private LocalDateTime lastLoginDate;
    private boolean online;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_uuid")
    private List<HomeEntity> homes;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "BALANCE_ID")
    private BalanceEntity balance;

    public MinecraftPlayerEntity(MinecraftPlayerSaveCommand command, double startBalance) {
        this.setAttributes(command);
        this.firstLoginDate = LocalDateTime.now();
        this.lastLoginDate = LocalDateTime.now();
        this.homes = new ArrayList<>();
        this.balance = new BalanceEntity(startBalance);
    }

    public void update(MinecraftPlayerSaveCommand command) {
        this.setAttributes(command);
        if (command.isOnline()) {
            this.lastLoginDate = LocalDateTime.now();
        }
    }

    private void setAttributes(MinecraftPlayerSaveCommand command) {
        this.uuid = command.getUuid();
        this.name = command.getName();
        this.online = command.isOnline();
    }

    public void increaseBalance(double amount) throws TransactionValidationException {
        this.balance.increase(amount);
    }

    public void decreaseBalance(double amount) throws TransactionValidationException {
        this.balance.decrease(amount);
    }
}
