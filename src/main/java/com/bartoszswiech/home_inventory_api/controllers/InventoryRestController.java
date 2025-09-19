package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.Inventory;
import com.bartoszswiech.home_inventory_api.exceptions.UserNotFoundException;
import com.bartoszswiech.home_inventory_api.services.HouseService;
import com.bartoszswiech.home_inventory_api.services.InventoryService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryRestController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories(@RequestParam Long houseId) {
        try {
            List<Inventory> inventories = inventoryService.findAll(houseId);
            return ResponseEntity.ok(inventories);
        }
        catch(UserNotFoundException ex) {
            return ResponseEntity.status(401).build();
        }
        catch( Exception ex ) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestParam Long houseId, @RequestBody Inventory newInventory) {
        try {
            Inventory createdInventory =  inventoryService.createInventory(houseId, newInventory);
            return ResponseEntity.ok(createdInventory);
        }
        catch(UserNotFoundException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventory(@PathVariable Long id) {
        try {
            Inventory foundInventory = inventoryService.findById(id);
            return ResponseEntity.ok(foundInventory);
        }
        catch(UserNotFoundException ex) {
            return ResponseEntity.status(401).build();
        }

    }

    @PutMapping
    public ResponseEntity<Inventory> updateInventory(@RequestBody Inventory updatedInventory) {
        try {

            Inventory foundInventory = inventoryService.update(updatedInventory);
            return ResponseEntity.ok(foundInventory);
        }
        catch(UserNotFoundException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) {
        try {
            inventoryService.deleteById(id);
            return ResponseEntity.ok(String.format("Successfully deleted inventory with id: %d", id));
        }
        catch(UserNotFoundException ex) {
            return ResponseEntity.status(401).build();
        }
    }
}
