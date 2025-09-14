package com.bartoszswiech.home_inventory_api.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User under provided username/email already exists.");
    }
}
