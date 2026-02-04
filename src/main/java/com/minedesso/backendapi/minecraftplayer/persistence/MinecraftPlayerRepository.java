package com.minedesso.backendapi.minecraftplayer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MinecraftPlayerRepository extends JpaRepository<MinecraftPlayerEntity, UUID> {
    Optional<MinecraftPlayerEntity> findByName(String name);
}
