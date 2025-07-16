package com.bartoszswiech.home_inventory_api.exceptions;

public class ItemsExistException extends RuntimeException {
    public ItemsExistException() {
        super("Room/Location has logged items, please clear all logged items before removing room/location.");
    }
}
