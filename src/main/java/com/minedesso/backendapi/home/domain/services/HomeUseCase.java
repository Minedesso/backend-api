package com.minedesso.backendapi.home.domain.services;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
import com.minedesso.backendapi.home.domain.dtos.out.Home;
import com.minedesso.backendapi.home.domain.utils.exceptions.HomeNotFoundException;

import java.util.List;
import java.util.UUID;

public interface HomeUseCase {

    void save(HomeSaveCommand homeSaveCommand);

    Home getHome(UUID ownerUuid, String homeName) throws HomeNotFoundException;

    List<Home> getAll(UUID ownerUuid);

    void deleteHome(UUID ownerUuid, String homeName) throws HomeNotFoundException;

}
