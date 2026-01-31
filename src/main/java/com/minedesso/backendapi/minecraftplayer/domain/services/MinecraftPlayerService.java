package com.minedesso.backendapi.minecraftplayer.domain.services;

import com.minedesso.backendapi.minecraftplayer.domain.dtos.in.MinecraftPlayerSaveCommand;
import com.minedesso.backendapi.minecraftplayer.domain.dtos.out.MinecraftPlayer;
import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MinecraftPlayerService implements MinecraftPlayerUseCase {
    private final MinecraftPlayerRepository repository;

    @Override
    public void save(MinecraftPlayerSaveCommand command) {
        Optional<MinecraftPlayerEntity> minecraftPlayerOpt = this.repository.findById(command.getUuid());

        MinecraftPlayerEntity minecraftPlayer;
        if (minecraftPlayerOpt.isPresent()) {
            minecraftPlayer = minecraftPlayerOpt.get();
            minecraftPlayer.update(command);
        } else {
            minecraftPlayer = new MinecraftPlayerEntity(command);
        }

        this.repository.save(minecraftPlayer);
    }

    @Override
    public List<MinecraftPlayer> getAll() {
        return this.repository.findAll().stream()
                .map(MinecraftPlayer::new)
                .toList();
    }

    @Override
    public MinecraftPlayer getById(UUID uuid) throws MinecraftPlayerNotFoundException {
        MinecraftPlayerEntity minecraftPlayer = this.repository.findById(uuid)
                .orElseThrow(() -> new MinecraftPlayerNotFoundException(uuid));
        return new MinecraftPlayer(minecraftPlayer);
    }

    @Override
    public void delete(UUID uuid) throws MinecraftPlayerNotFoundException {
        if (!this.repository.existsById(uuid)) {
            throw new MinecraftPlayerNotFoundException(uuid);
        }
        this.repository.deleteById(uuid);
    }
}
