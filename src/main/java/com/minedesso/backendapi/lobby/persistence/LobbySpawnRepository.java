package com.minedesso.backendapi.lobby.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LobbySpawnRepository extends JpaRepository<LobbySpawnEntity, Long> {
}
