package com.minedesso.backendapi.ban.domain.services;

import com.minedesso.backendapi.ban.domain.dtos.out.Reason;

import java.util.List;

public interface ReasonUseCase {

    List<Reason> getAllReasons();

    boolean validateReason(long reasonId);

}
