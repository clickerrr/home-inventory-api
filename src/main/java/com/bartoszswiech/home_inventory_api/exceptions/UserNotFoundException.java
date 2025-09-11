package com.bartoszswiech.home_inventory_api.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Provided user not found.");
    }
}
