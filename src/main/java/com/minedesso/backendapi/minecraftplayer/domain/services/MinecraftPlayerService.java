package com.minedesso.backendapi.minecraftplayer.domain.services;

import com.minedesso.backendapi.minecraftplayer.domain.dtos.in.MinecraftPlayerSaveCommand;
import com.minedesso.backendapi.minecraftplayer.domain.dtos.out.MinecraftPlayer;
import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerRepository;
import com.minedesso.backendapi.settings.persistence.SettingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MinecraftPlayerService implements MinecraftPlayerUseCase {
    private final MinecraftPlayerRepository minecraftPlayerRepository;
    private final SettingsRepository settingsRepository;

    @Override
    public void save(MinecraftPlayerSaveCommand command) {
        Optional<MinecraftPlayerEntity> minecraftPlayerEntityOpt = this.minecraftPlayerRepository
                .findById(command.getUuid());

        MinecraftPlayerEntity minecraftPlayerEntity;
        if (minecraftPlayerEntityOpt.isPresent()) {
            minecraftPlayerEntity = minecraftPlayerEntityOpt.get();
            minecraftPlayerEntity.update(command);
        } else {
            minecraftPlayerEntity = new MinecraftPlayerEntity(command, this.settingsRepository.findStartBalance());
        }

        this.minecraftPlayerRepository.save(minecraftPlayerEntity);
    }

    @Override
    public List<MinecraftPlayer> getAll() {
        return this.minecraftPlayerRepository.findAll().stream()
                .map(MinecraftPlayer::new)
                .toList();
    }

    @Override
    public MinecraftPlayer getById(UUID uuid) throws MinecraftPlayerNotFoundException {
        MinecraftPlayerEntity minecraftPlayerEntity = this.minecraftPlayerRepository.findById(uuid)
                .orElseThrow(() -> new MinecraftPlayerNotFoundException(uuid));
        return new MinecraftPlayer(minecraftPlayerEntity);
    }

    @Override
    public void delete(UUID uuid) throws MinecraftPlayerNotFoundException {
        if (!this.minecraftPlayerRepository.existsById(uuid)) {
            throw new MinecraftPlayerNotFoundException(uuid);
        }
        this.minecraftPlayerRepository.deleteById(uuid);
    }
}
