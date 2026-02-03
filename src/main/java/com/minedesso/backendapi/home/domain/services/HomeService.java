package com.minedesso.backendapi.home.domain.services;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
import com.minedesso.backendapi.home.domain.dtos.out.Home;
import com.minedesso.backendapi.home.domain.utils.exceptions.HomeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService implements HomeUseCase {
    @Override
    public void save(HomeSaveCommand homeSaveCommand) {

    }

    @Override
    public Home getHome(String ownerUuid, String homeName) throws HomeNotFoundException {
        return null;
    }

    @Override
    public List<Home> getAll(String ownerUuid) {
        return List.of();
    }

    @Override
    public void deleteHome(String ownerUuid, String homeName) throws HomeNotFoundException {

    }
}
