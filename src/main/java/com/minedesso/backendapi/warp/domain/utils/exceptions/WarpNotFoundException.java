package com.minedesso.backendapi.warp.domain.utils.exceptions;

public class WarpNotFoundException extends Exception {
    public WarpNotFoundException(String name) {
        super("Warp " + name + " not found.");
    }
}
