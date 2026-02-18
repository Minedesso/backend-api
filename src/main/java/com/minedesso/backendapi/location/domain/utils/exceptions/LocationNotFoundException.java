package com.minedesso.backendapi.location.domain.utils.exceptions;

public class LocationNotFoundException extends Exception {
    public LocationNotFoundException(String name) {
        super("Location with name " + name + " not found!");
    }
}
