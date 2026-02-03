package com.minedesso.backendapi.home.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<HomeEntity, Long> {
}
