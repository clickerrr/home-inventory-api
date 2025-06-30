package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.*;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.*;
import jakarta.transaction.Transactional;
import org.apache.juli.logging.Log;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoggedItemService {


    private final LoggedItemRepository loggedItemRepository;
    private final LocationRepository locationRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public LoggedItemService(LoggedItemRepository loggedItemRepository,
                             LocationRepository locationRepository,
                             InventoryRepository inventoryRepository,
                             ProductRepository productRepository) {
        this.loggedItemRepository = loggedItemRepository;
        this.locationRepository = locationRepository;
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public LoggedItem createLoggedItem(LoggedItem newLoggedItem, String upca, Long locationId, Long inventoryId) {
        // Check if the LoggedItem already exists
        if (newLoggedItem.getId() != null && loggedItemRepository.findById(newLoggedItem.getId()).isPresent()) {
            throw new EntryAlreadyExistsException(newLoggedItem.getId().toString());
        }

        // Fetch the Location and Inventory entities
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new EntryNotFoundException("Location not found with ID: " + locationId));

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new EntryNotFoundException("Inventory not found with ID: " + inventoryId));

        // Fetch the Product entity
        Product product = productRepository.findById(upca)
                .orElseThrow(() -> new EntryNotFoundException("Product not found with UPCA: " + upca));
        System.out.println(product);
        // Set the relationships
        newLoggedItem.setLocation(location);
        newLoggedItem.setInventory(inventory);
        newLoggedItem.setProduct(product);

        // Save the LoggedItem
        if(newLoggedItem.getDateLogged() == null) {
            newLoggedItem.setDateLogged(LocalDate.now());
            LocalDate dateLogged = newLoggedItem.getDateLogged();
            newLoggedItem.setExpirationDate(dateLogged.plusWeeks(1));
            newLoggedItem.setConsumeByDate(dateLogged.plusWeeks(1));
        }
        LoggedItem savedLoggedItem = loggedItemRepository.save(newLoggedItem);
        System.out.println("Saved logged item");
        // Update the bidirectional relationships
        location.getLoggedItems().add(savedLoggedItem);
        inventory.getLoggedItems().add(savedLoggedItem);


        return savedLoggedItem;
    }

    public List<LoggedItem> findAll() {
        return loggedItemRepository.findAll();
    }

    public LoggedItem findById(Long id) {
        return loggedItemRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));
    }


    public LoggedItem update(Long id, LoggedItem newLoggedItem) {
        return loggedItemRepository.findById(id)
                .map(item -> {

                    // TODO: Update logged item repository
                return new LoggedItem();
                })
                .orElseGet(() -> {
                    return loggedItemRepository.save(newLoggedItem);
                });
    }

    public void deleteById(Long id) {
        loggedItemRepository.deleteById(id);
    }
}
