package com.minedesso.backendapi.location.domain.services;

import com.minedesso.backendapi.location.domain.dtos.in.LocationSaveCommand;
import com.minedesso.backendapi.location.domain.dtos.out.Location;
import com.minedesso.backendapi.location.domain.utils.exceptions.LocationNotFoundException;

public interface LocationUseCase {
    void save(LocationSaveCommand command);

    Location getByName(String name) throws LocationNotFoundException;

    void delete(String name) throws LocationNotFoundException;
}
