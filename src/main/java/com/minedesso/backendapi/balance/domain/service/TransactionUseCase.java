package com.minedesso.backendapi.balance.domain.service;

import com.minedesso.backendapi.balance.domain.dtos.in.TransactionCommand;
import com.minedesso.backendapi.balance.domain.utils.exceptions.TransactionValidationException;

public interface TransactionUseCase {
    void transact(TransactionCommand command) throws TransactionValidationException;
}
