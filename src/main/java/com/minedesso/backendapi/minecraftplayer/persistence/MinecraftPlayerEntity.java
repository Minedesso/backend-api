package com.minedesso.backendapi.minecraftplayer.persistence;

import com.minedesso.backendapi.ban.persistence.BanEntity;
import com.minedesso.backendapi.home.persistence.HomeEntity;
import com.minedesso.backendapi.minecraftplayer.domain.dtos.in.MinecraftPlayerSaveCommand;
import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import com.minedesso.backendapi.moneyflow.persistence.MoneyFlowEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_uuid")
    private List<HomeEntity> homes;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MONEY_FLOW_ID")
    private MoneyFlowEntity moneyFlow;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "target_uuid")
    private List<BanEntity> bans;


    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "banned_by_uuid")
    private List<BanEntity> banned;

    public MinecraftPlayerEntity(MinecraftPlayerSaveCommand command, double startMoneyFlowBalance) {
        this.setAttributes(command);
        if (command.isOnline()) {
            this.firstLoginDate = LocalDateTime.now();
            this.lastLoginDate = LocalDateTime.now();
        }
        this.moneyFlow = new MoneyFlowEntity(startMoneyFlowBalance);
        this.homes = new ArrayList<>();
        this.bans = new ArrayList<>();
        this.banned = new ArrayList<>();
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

    public void increaseMoneyFlowBalance(double amount) throws TransactionValidationException {
        this.moneyFlow.increase(amount);
    }

    public void decreaseMoneyFlowBalance(double amount) throws TransactionValidationException {
        this.moneyFlow.decrease(amount);
    }

    public void setMoneyFlowBalance(double amount) throws TransactionValidationException {
        this.moneyFlow.setBalance(amount);
    }

    public void addHome(HomeEntity home) {
        this.homes.add(home);
    }

    public void removeHome(HomeEntity home) {
        this.homes.remove(home);
    }

    public Optional<HomeEntity> getHomeByName(String name) {
        return this.homes.stream()
                .filter(h -> h.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    public void addBan(BanEntity ban) {
        this.bans.add(ban);
    }

}
