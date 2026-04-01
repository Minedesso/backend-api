package com.minedesso.backendapi.ban.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonRepository extends JpaRepository<ReasonEntity, Long> {
}
