package com.minedesso.backendapi.moneyflow.web;

import com.minedesso.backendapi.minecraftplayer.domain.utils.exceptions.MinecraftPlayerNotFoundException;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.SetMoneyFlowBalanceOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.in.TransactionOfflineCommand;
import com.minedesso.backendapi.moneyflow.domain.dtos.out.Balance;
import com.minedesso.backendapi.moneyflow.domain.service.MoneyFlowUseCase;
import com.minedesso.backendapi.moneyflow.domain.utils.exceptions.TransactionValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
public class MoneyFlowController {
    private final MoneyFlowUseCase useCase;

    @PostMapping("/pay")
    public ResponseEntity<Void> transact(@RequestBody TransactionCommand command) {
        try {
            this.useCase.transact(command);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionValidationException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/pay/offline")
    public ResponseEntity<Void> transactWithOfflinePlayer(@RequestBody TransactionOfflineCommand command) {
        try {
            this.useCase.transactWithOfflinePlayer(command);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MinecraftPlayerNotFoundException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (TransactionValidationException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/money-flow/balance")
    public ResponseEntity<Void> setBalance(@RequestBody SetMoneyFlowBalanceCommand command) {
        try {
            this.useCase.setBalance(command);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionValidationException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/money-flow/balance/offline")
    public ResponseEntity<Void> setBalanceOfOfflinePlayer(@RequestBody SetMoneyFlowBalanceOfflineCommand command) {
        try {
            this.useCase.setBalanceOfOfflinePlayer(command);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (MinecraftPlayerNotFoundException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (TransactionValidationException e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/money-flow/balance/{owner-uuid}")
    public ResponseEntity<Balance> getBalance(@PathVariable(name = "owner-uuid") UUID ownerUuid) {
        try {
            Balance balance = this.useCase.getMoneyFlowBalance(ownerUuid);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
