package com.minedesso.backendapi.ban.domain.util.exceptions;

public class ReasonNotFoundException extends Exception {
    public ReasonNotFoundException(long id) {
        super("Reason with id " + id + " not found.");
    }
}
