package com.minedesso.backendapi.warp.domain.services;

import com.minedesso.backendapi.warp.domain.dtos.in.WarpSaveCommand;
import com.minedesso.backendapi.warp.domain.dtos.out.Warp;
import com.minedesso.backendapi.warp.domain.utils.exceptions.WarpNotFoundException;

import java.util.List;

public interface WarpUseCase {

    void save(WarpSaveCommand warpSaveCommand);

    List<Warp> getAll();

    Warp getByName(String name) throws WarpNotFoundException;

    void deleteByName(String name)throws WarpNotFoundException;

}
