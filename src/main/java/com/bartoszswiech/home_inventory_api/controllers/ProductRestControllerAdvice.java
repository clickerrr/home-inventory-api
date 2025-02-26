package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ProductControllerAdvice {

    @ExceptionHandler(EntryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entryNotFoundHandler(EntryNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EntryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String entryAlreadyExistsHandler(EntryAlreadyExistsException ex) {
        return ex.getMessage();
    }

}