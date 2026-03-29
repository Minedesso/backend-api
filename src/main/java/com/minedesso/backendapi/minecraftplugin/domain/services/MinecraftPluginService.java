package com.minedesso.backendapi.minecraftplugin.domain.services;

import com.minedesso.backendapi.minecraftplugin.domain.dtos.in.MinecraftPluginSaveCommand;
import com.minedesso.backendapi.minecraftplugin.domain.dtos.out.MinecraftPlugin;
import com.minedesso.backendapi.minecraftplugin.persistence.MinecraftPluginEntity;
import com.minedesso.backendapi.minecraftplugin.persistence.MinecraftPluginRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MinecraftPluginService implements MinecraftPluginUseCase {
    private final MinecraftPluginRepository minecraftPluginRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(MinecraftPluginSaveCommand command) {
        MinecraftPluginEntity minecraftPluginEntity;
        if (command.getId() != null) {
            minecraftPluginEntity = this.minecraftPluginRepository.findById(command.getId())
                    .orElseThrow(() -> new IllegalStateException("plugin not found"));
            minecraftPluginEntity.update(command.getName());
            // TODO Existiert dat ding bereits?
        } else {
            String apiKey = this.passwordEncoder.encode(command.getApiKey());
            minecraftPluginEntity = new MinecraftPluginEntity(command.getName(), apiKey);
        }
        this.minecraftPluginRepository.save(minecraftPluginEntity);
    }

    @Override
    public List<MinecraftPlugin> getAll() {
        return this.minecraftPluginRepository.findAll().stream()
                .map(MinecraftPlugin::new)
                .toList();
    }

    @Override
    public void delete(long id) {
        if (!this.minecraftPluginRepository.existsById(id)) {
            // TODO THROW EXCEPTION
        }
        this.minecraftPluginRepository.deleteById(id);
    }
}
