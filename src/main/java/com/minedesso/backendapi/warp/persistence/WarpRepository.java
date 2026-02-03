package com.minedesso.backendapi.warp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WarpRepository extends JpaRepository<WarpEntity, String> {
}
