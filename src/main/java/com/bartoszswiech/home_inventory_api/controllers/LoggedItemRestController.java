package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.LoggedItem;
import com.bartoszswiech.home_inventory_api.beans.Product;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.LoggedItemRepository;
import com.bartoszswiech.home_inventory_api.services.LoggedItemService;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class LoggedItemRestController {
    private static final Logger log = LoggerFactory.getLogger(LoggedItemRestController.class);

    private LoggedItemService loggedItemService;
    public LoggedItemRestController( LoggedItemService loggedItemService ) { this.loggedItemService = loggedItemService;}

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/loggedItems")
    List<LoggedItem> all() {

        return loggedItemService.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/loggedItem")
    LoggedItem newLoggedItem(@RequestBody LoggedItem newLoggedItem, @RequestParam String upca, @RequestParam Long locationId,
                             @RequestParam Long inventoryId) {

        return loggedItemService.createLoggedItem(newLoggedItem, upca, locationId, inventoryId);
    }

    // Single item
    @GetMapping("/loggedItem/{id}")
    LoggedItem getProduct(@PathVariable Long id) {

        return loggedItemService.findById(id);
    }

    @PutMapping("/loggedItem/{id}")
    LoggedItem replaceProduct(@RequestBody LoggedItem newLoggedItem, @PathVariable Long id) {

        return loggedItemService.update(id, newLoggedItem );
    }

    @DeleteMapping("/loggedItem/{id}")
    void deleteProduct(@PathVariable Long id) {
        loggedItemService.deleteById(id);
    }
}
