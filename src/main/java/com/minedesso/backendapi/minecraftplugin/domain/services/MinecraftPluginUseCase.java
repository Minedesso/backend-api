package com.minedesso.backendapi.minecraftplugin.domain.services;

import com.minedesso.backendapi.minecraftplugin.domain.dtos.in.MinecraftPluginSaveCommand;
import com.minedesso.backendapi.minecraftplugin.domain.dtos.out.MinecraftPlugin;

import java.util.List;

public interface MinecraftPluginUseCase {
    void save(MinecraftPluginSaveCommand command);

    List<MinecraftPlugin> getAll();

    void delete(long id);
}
