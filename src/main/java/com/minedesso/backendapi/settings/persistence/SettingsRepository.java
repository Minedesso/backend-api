package com.minedesso.backendapi.settings.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity, Long> {
    @Query("""
            SELECT s.startBalance
            FROM SettingsEntity s
            WHERE s.id = 10000
            """)
    double findStartBalance();
}
