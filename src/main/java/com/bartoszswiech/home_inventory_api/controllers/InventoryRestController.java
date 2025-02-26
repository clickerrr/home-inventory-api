package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.Inventory;
import com.bartoszswiech.home_inventory_api.services.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryRestController {

    private final InventoryService inventoryService;

    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> getAllInventories() {
        return inventoryService.findAll();
    }

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory newInventory) {
        return inventoryService.createInventory(newInventory);
    }

    @GetMapping("/{id}")
    public Inventory getInventory(@PathVariable Long id) {
        return inventoryService.findById(id);
    }

    @PutMapping("/{id}")
    public Inventory updateInventory(@RequestBody Inventory updatedInventory, @PathVariable Long id) {
        return inventoryService.update(id, updatedInventory);
    }

    @DeleteMapping("/{id}")
    public void deleteInventory(@PathVariable Long id) {
        inventoryService.deleteById(id);
    }
}
