package com.minedesso.backendapi.home.domain.utils.exceptions;

public class HomeAlreadyExistsException extends RuntimeException {
    public HomeAlreadyExistsException(String name) {
        super("Home with name '" + name + "' already exists.");
    }
}
