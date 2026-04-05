package com.minedesso.backendapi.ban.web;

import com.minedesso.backendapi.ban.domain.dtos.out.Reason;
import com.minedesso.backendapi.ban.domain.services.ReasonUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reason")
@RequiredArgsConstructor
public class ReasonController {

    private final ReasonUseCase reasonUseCase;

    @GetMapping("/all")
    public ResponseEntity<List<Reason>> getAllReasons() {
        return ResponseEntity.ok(reasonUseCase.getAllReasons());
    }

    @GetMapping("/check/{reason-id}")
    public ResponseEntity<Boolean> validateReason(@PathVariable(name = "reason-id") long reasonId) {
        return ResponseEntity.ok(reasonUseCase.validateReason(reasonId));
    }

}
