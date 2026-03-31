package com.minedesso.backendapi.minecraftplugin.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MinecraftPluginRepository extends JpaRepository<MinecraftPluginEntity, Long> {
    Optional<MinecraftPluginEntity> findByName(String name);
}
