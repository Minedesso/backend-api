package com.minedesso.backendapi.moneyflow.domain.utils.exceptions;

public class TransactionValidationException extends Exception {
    public TransactionValidationException(String message) {
        super(message);
    }
}
