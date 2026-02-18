package com.minedesso.backendapi.location.domain.services;

import com.minedesso.backendapi.location.domain.dtos.in.LocationSaveCommand;
import com.minedesso.backendapi.location.domain.dtos.out.Location;
import com.minedesso.backendapi.location.domain.utils.exceptions.LocationNotFoundException;
import com.minedesso.backendapi.location.persistence.LocationEntity;
import com.minedesso.backendapi.location.persistence.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationService implements LocationUseCase {
    private final LocationRepository repository;

    @Override
    public void save(LocationSaveCommand command) {
        Optional<LocationEntity> locationEntityOpt = this.repository.findById(command.getName());
        LocationEntity locationEntity;
        if (locationEntityOpt.isPresent()) {
            locationEntity = locationEntityOpt.get();
            locationEntity.update(command);
        } else {
            locationEntity = new LocationEntity(command);
        }
        this.repository.save(locationEntity);
    }

    @Override
    public Location getByName(String name) throws LocationNotFoundException {
        LocationEntity locationEntity = this.repository.findById(name)
                .orElseThrow(() -> new LocationNotFoundException(name));
        return new Location(locationEntity);
    }

    @Override
    public void delete(String name) throws LocationNotFoundException {
        if (!this.repository.existsById(name)) {
            throw new LocationNotFoundException(name);
        }
        this.repository.deleteById(name);
    }
}
