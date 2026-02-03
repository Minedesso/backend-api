package com.minedesso.backendapi.home.domain.services;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
import com.minedesso.backendapi.home.domain.dtos.out.Home;
import com.minedesso.backendapi.home.domain.utils.exceptions.HomeNotFoundException;

import java.util.List;

public interface HomeUseCase {

    void save(HomeSaveCommand homeSaveCommand);

    Home getHome(String ownerUuid, String homeName) throws HomeNotFoundException;

    List<Home> getAll(String ownerUuid);

    void deleteHome(String ownerUuid, String homeName) throws HomeNotFoundException;

}
