package com.minedesso.backendapi.balance.domain.utils.exceptions;

public class TransactionValidationException extends Exception {
    public TransactionValidationException(String message) {
        super(message);
    }
}
