package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.LoggedItem;
import com.bartoszswiech.home_inventory_api.beans.Product;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.exceptions.UserNotFoundException;
import com.bartoszswiech.home_inventory_api.interfaces.LoggedItemWithProduct;
import com.bartoszswiech.home_inventory_api.repositories.LoggedItemRepository;
import com.bartoszswiech.home_inventory_api.services.InventoryService;
import com.bartoszswiech.home_inventory_api.services.LoggedItemService;
import org.apache.coyote.Response;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class LoggedItemRestController {
    private static final Logger log = LoggerFactory.getLogger(LoggedItemRestController.class);

    @Autowired
    private LoggedItemService loggedItemService;
    @Autowired
    private InventoryService inventoryService;

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/loggedItems")
    List<LoggedItem> all() {
        return loggedItemService.findAll();
    }
    // end::get-aggregate-root[]

    @GetMapping("/newLoggedItems")
    public ResponseEntity<List<LoggedItemWithProduct>> allLoggedItemsWithInventory(@RequestParam Long inventoryId) {
        try {
            List<LoggedItemWithProduct> loggedItems = inventoryService.getAllLoggedItemsWithProduct(inventoryId);
            return ResponseEntity.ok(loggedItems);
        }
        catch(UserNotFoundException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/loggedItem")
    public ResponseEntity<LoggedItem> newLoggedItem(@RequestBody LoggedItem newLoggedItem, @RequestParam String upca, @RequestParam Long locationId,
                             @RequestParam Long inventoryId) {

        try {
            System.out.println("Creating new logged item");
            LoggedItem createdLoggedItem = loggedItemService.createLoggedItem(newLoggedItem, upca, locationId, inventoryId);
            return ResponseEntity.ok(createdLoggedItem);
        }
        catch(UserNotFoundException ex) {
            return ResponseEntity.status(401).build();
        }

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
        System.out.println(id);
        loggedItemService.deleteById(id);
    }
}
