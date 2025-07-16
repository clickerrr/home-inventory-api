package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.Location;
import com.bartoszswiech.home_inventory_api.beans.LoggedItem;
import com.bartoszswiech.home_inventory_api.beans.Product;
import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.exceptions.ItemsExistException;
import com.bartoszswiech.home_inventory_api.interfaces.LoggedItemWithProduct;
import com.bartoszswiech.home_inventory_api.repositories.LocationRepository;
import com.bartoszswiech.home_inventory_api.repositories.LoggedItemRepository;
import com.bartoszswiech.home_inventory_api.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;
    private final LoggedItemRepository loggedItemRepository;

    public LocationService(LocationRepository locationRepository, RoomRepository roomRepository, LoggedItemRepository loggedItemRepository) {
        this.locationRepository = locationRepository;
        this.roomRepository = roomRepository;
        this.loggedItemRepository = loggedItemRepository;
    }

    @Transactional
    public Location createLocation(Location newLocation, Long roomId) {
        if (newLocation.getId() != null && locationRepository.existsById(newLocation.getId())) {
            throw new EntryAlreadyExistsException(newLocation.getId().toString());
        }

        // Fetch the Room entity
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntryNotFoundException("Room not found with id: " + roomId));

        newLocation.setRoom(room);
        Location savedLocation = locationRepository.save(newLocation);

        room.getLocations().add(savedLocation);

        return savedLocation;
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public Location findById(Long id) {

        return locationRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));
    }

    public List<LoggedItemWithProduct> getLocationLoggedItems(Long id) {
        Location location = locationRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));
        Set<LoggedItem> locationItems = location.getLoggedItems();
        List<LoggedItemWithProduct> fullLoggedItems = new ArrayList<LoggedItemWithProduct>();
        locationItems.forEach( loggedItem -> {
            LoggedItemWithProduct result = loggedItemRepository.findLoggedItemWithProduct(loggedItem.getId());
            if(result != null) {
                fullLoggedItems.add(result);
            }
        });
        return fullLoggedItems;
    }


    @Transactional
    public Location update(Long id, Location updatedLocation) {

        return locationRepository.findById(id)
                .map(location -> {

                    // Update fields as necessary
                    location.setTitle(updatedLocation.getTitle());

                    return locationRepository.save(location);
                })
                .orElseGet(() -> {
                    return locationRepository.save(updatedLocation);
                });
    }

    public boolean deleteById(Long id) {

        Location returnedLocation = locationRepository.findById(id).orElseThrow( () -> new EntryNotFoundException(id.toString()));
        if(!returnedLocation.getLoggedItems().isEmpty()) throw new ItemsExistException();
        locationRepository.deleteById(id);
        return true;
    }
}
