package com.minedesso.backendapi.warp.domain.services;

import com.minedesso.backendapi.warp.domain.dtos.in.WarpSaveCommand;
import com.minedesso.backendapi.warp.domain.dtos.out.Warp;
import com.minedesso.backendapi.warp.domain.utils.exceptions.WarpNotFoundException;
import com.minedesso.backendapi.warp.persistence.WarpEntity;
import com.minedesso.backendapi.warp.persistence.WarpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarpService implements WarpUseCase {

    private final WarpRepository warpRepository;

    @Override
    public void save(WarpSaveCommand warpSaveCommand) {
        warpRepository.save(new WarpEntity(warpSaveCommand));
    }

    @Override
    public List<Warp> getAll() {
        return warpRepository.findAll()
                .stream().map(Warp::new)
                .toList();
    }

    @Override
    public Warp getByName(String name) throws WarpNotFoundException {
        WarpEntity warpEntity = warpRepository.findById(name)
                .orElseThrow(() -> new WarpNotFoundException(name));

        return new Warp(warpEntity);
    }

    @Override
    public void deleteByName(String name) throws WarpNotFoundException{
        if(!warpRepository.existsById(name)) {
            throw new WarpNotFoundException(name);
        }
        warpRepository.deleteById(name);
    }
}
