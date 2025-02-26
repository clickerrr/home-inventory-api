package com.bartoszswiech.home_inventory_api.exceptions;

public class EntryAlreadyExistsException extends RuntimeException {
    public EntryAlreadyExistsException(String upca) {
        super(String.format("Product with upca: (%s) already exists", upca));
    }
}
