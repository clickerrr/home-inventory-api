package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.beans.Inventory;
import com.bartoszswiech.home_inventory_api.beans.LoggedItem;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.exceptions.UserNotFoundException;
import com.bartoszswiech.home_inventory_api.interfaces.LoggedItemWithProduct;
import com.bartoszswiech.home_inventory_api.repositories.HouseRepository;
import com.bartoszswiech.home_inventory_api.repositories.InventoryRepository;
import com.bartoszswiech.home_inventory_api.repositories.LoggedItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private HouseService houseService;
    @Autowired
    private LoggedItemRepository loggedItemRepository;

    @Transactional
    public Inventory createInventory(Long houseId, Inventory newInventory) {
        House foundHouse = houseService.findById(houseId);
        if (newInventory.getId() != null && inventoryRepository.existsById(newInventory.getId())) {
            throw new EntryAlreadyExistsException(newInventory.getId().toString());
        }
        newInventory.setAssociatedHouse(foundHouse);

        return inventoryRepository.save(newInventory);
    }

    public List<Inventory> findAll(Long houseId) throws UserNotFoundException {


        House foundHouse = houseService.findById(houseId);

        Set<Inventory> inventories = foundHouse.getInventories();

        return inventories.stream().toList();
    }

    public Inventory findById(Long id) throws UserNotFoundException {

        Inventory foundInventory = inventoryRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));


        House foundHouse = foundInventory.getAssociatedHouse();

        houseService.checkIfCurrentUserInHouse(foundHouse);

        return foundInventory;
    }

    @Transactional
    public Inventory update(Inventory updatedInventory) {

        Long inventoryId = updatedInventory.getId();
        if(inventoryId == null) {
            throw new IllegalArgumentException();
        }
        Inventory foundInventory = inventoryRepository.findById(inventoryId).orElseThrow();
        House foundHouse = foundInventory.getAssociatedHouse();

        houseService.checkIfCurrentUserInHouse(foundHouse);

        foundInventory.setTitle(updatedInventory.getTitle());

        return inventoryRepository.save(foundInventory);
    }

    public void deleteById(Long inventoryId) {

        Inventory foundInventory = inventoryRepository.findById(inventoryId).orElseThrow();

        houseService.checkIfCurrentUserInHouse(foundInventory.getAssociatedHouse());

        foundInventory.removeAssociatedHouse();

        inventoryRepository.delete(foundInventory);
    }

    public List<LoggedItemWithProduct> getAllLoggedItemsWithProduct(Long inventoryId) {
        Inventory foundInventory = inventoryRepository.findById(inventoryId).orElseThrow();
        houseService.checkIfCurrentUserInHouse(foundInventory.getAssociatedHouse());

        Set<LoggedItem> loggedItems = foundInventory.getLoggedItems();
        List<LoggedItemWithProduct> fullLoggedItems = new ArrayList<LoggedItemWithProduct>();

        loggedItems.forEach( loggedItem -> {
            LoggedItemWithProduct result = loggedItemRepository.findLoggedItemWithProduct(loggedItem.getId());
            if(result != null) {
                fullLoggedItems.add(result);
            }
        });

        return fullLoggedItems;
    }


}
