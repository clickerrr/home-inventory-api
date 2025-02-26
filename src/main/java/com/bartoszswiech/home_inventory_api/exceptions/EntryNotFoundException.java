package com.bartoszswiech.home_inventory_api.exceptions;

public class EntryNotFoundException extends RuntimeException {
    public EntryNotFoundException(String upca) {
        super("Could not find product with upca: " + upca);
    }
}
