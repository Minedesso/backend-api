package com.minedesso.backendapi.home.domain.services;

import com.minedesso.backendapi.home.domain.dtos.in.HomeSaveCommand;
import com.minedesso.backendapi.home.domain.dtos.out.Home;
import com.minedesso.backendapi.home.domain.utils.exceptions.HomeAlreadyExistsException;
import com.minedesso.backendapi.home.domain.utils.exceptions.HomeNotFoundException;
import com.minedesso.backendapi.home.persistence.HomeEntity;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerEntity;
import com.minedesso.backendapi.minecraftplayer.persistence.MinecraftPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeService implements HomeUseCase {

    private final MinecraftPlayerRepository minecraftPlayerRepository;

    @Override
    public void save(HomeSaveCommand homeSaveCommand) throws HomeAlreadyExistsException {
        MinecraftPlayerEntity ownerEntity = this.minecraftPlayerRepository.findById(homeSaveCommand.getOwnerUuid())
                .orElseThrow(() -> new IllegalStateException("Owner not found"));

        if(ownerEntity.getHomeByName(homeSaveCommand.getName()).isPresent()) {
            throw new HomeAlreadyExistsException(homeSaveCommand.getName());
        }

        HomeEntity homeEntity = new HomeEntity(homeSaveCommand);
        ownerEntity.addHome(homeEntity);
        this.minecraftPlayerRepository.save(ownerEntity);
    }

    @Override
    public Home getHome(UUID ownerUuid, String homeName) throws HomeNotFoundException {
        MinecraftPlayerEntity ownerEntity = this.minecraftPlayerRepository.findById(ownerUuid)
                .orElseThrow(() -> new IllegalStateException("Owner not found"));

        HomeEntity homeEntity = ownerEntity.getHomeByName(homeName)
                .orElseThrow(() -> new HomeNotFoundException(homeName));

        return new Home(homeEntity);
    }

    @Override
    public List<Home> getAllOfPlayer(UUID ownerUuid) {
        return this.minecraftPlayerRepository.findById(ownerUuid)
                .orElseThrow(() -> new IllegalStateException("Owner not found"))
                .getHomes().stream()
                .map(Home::new)
                .toList();
    }

    @Override
    public void deleteHome(UUID ownerUuid, String homeName) throws HomeNotFoundException {
        MinecraftPlayerEntity ownerEntity = this.minecraftPlayerRepository.findById(ownerUuid)
                .orElseThrow(() -> new IllegalStateException("Owner not found"));

        HomeEntity homeEntity = ownerEntity.getHomeByName(homeName)
                .orElseThrow(() -> new HomeNotFoundException(homeName));

        ownerEntity.removeHome(homeEntity);
        this.minecraftPlayerRepository.save(ownerEntity);
    }
}
