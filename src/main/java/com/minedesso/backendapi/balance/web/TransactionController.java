package com.minedesso.backendapi.balance.web;

import com.minedesso.backendapi.balance.domain.dtos.in.TransactionCommand;
import com.minedesso.backendapi.balance.domain.service.TransactionUseCase;
import com.minedesso.backendapi.balance.domain.utils.exceptions.TransactionValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Slf4j
@AllArgsConstructor
public class TransactionController {
    private final TransactionUseCase useCase;

    @PostMapping
    public ResponseEntity<Void> transact(@RequestBody TransactionCommand command) {
        try {
            this.useCase.transact(command);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionValidationException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
