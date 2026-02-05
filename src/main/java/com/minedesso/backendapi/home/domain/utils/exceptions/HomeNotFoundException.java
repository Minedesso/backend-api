package com.minedesso.backendapi.home.domain.utils.exceptions;

public class HomeNotFoundException extends Exception {
    public HomeNotFoundException(String name) {
        super("Home with name '" + name + "' not found.");
    }
}
