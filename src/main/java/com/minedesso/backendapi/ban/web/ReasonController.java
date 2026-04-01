package com.minedesso.backendapi.ban.web;

import com.minedesso.backendapi.ban.persistence.ReasonEntity;
import com.minedesso.backendapi.ban.persistence.ReasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/reason")
@RequiredArgsConstructor
public class ReasonController {

    private final ReasonRepository reasonRepository;

    @GetMapping("/all")
    public ResponseEntity<List<ReasonEntity>> getAllReasons() {
        return ResponseEntity.ok(reasonRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(ReasonEntity::getReasonId))
                .toList());
    }

    @GetMapping("/check/{reason-id}")
    public ResponseEntity<Boolean> validateReason(@PathVariable(name = "reason-id") Long reasonId) {
        return ResponseEntity.ok(reasonRepository.findById(reasonId).isPresent());
    }

}
