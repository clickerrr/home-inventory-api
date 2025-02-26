package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.Inventory;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public Inventory createInventory(Inventory newInventory) {
        if (newInventory.getId() != null && inventoryRepository.existsById(newInventory.getId())) {
            throw new EntryAlreadyExistsException(newInventory.getId().toString());
        }
        return inventoryRepository.save(newInventory);
    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    public Inventory findById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));
    }

    @Transactional
    public Inventory update(Long id, Inventory updatedInventory) {
        return inventoryRepository.findById(id)
                .map(inventory -> {
                    // Update fields as necessary
                    return inventoryRepository.save(inventory);
                })
                .orElseGet(() -> {
                    return inventoryRepository.save(updatedInventory);
                });
    }

    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }
}
