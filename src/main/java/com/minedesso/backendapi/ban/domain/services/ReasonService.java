package com.minedesso.backendapi.ban.domain.services;

import com.minedesso.backendapi.ban.domain.dtos.out.Reason;
import com.minedesso.backendapi.ban.persistence.ReasonEntity;
import com.minedesso.backendapi.ban.persistence.ReasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReasonService implements ReasonUseCase{

    private final ReasonRepository reasonRepository;

    @Override
    public List<Reason> getAllReasons() {
        return reasonRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ReasonEntity::getReasonId))
                .map(Reason::new)
                .toList();
    }

    @Override
    public boolean validateReason(long reasonId) {
        return reasonRepository.findById(reasonId).isPresent();
    }
}
