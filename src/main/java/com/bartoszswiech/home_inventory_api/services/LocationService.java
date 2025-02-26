package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.Location;
import com.bartoszswiech.home_inventory_api.beans.Product;
import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.LocationRepository;
import com.bartoszswiech.home_inventory_api.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;

    public LocationService(LocationRepository locationRepository, RoomRepository roomRepository) {
        this.locationRepository = locationRepository;
        this.roomRepository = roomRepository;
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

    @Transactional
    public Location update(Long id, Location updatedLocation) {
        return locationRepository.findById(id)
                .map(location -> {
                    // Update fields as necessary
                    location.setTitle(updatedLocation.getTitle());
                    location.setRoom(updatedLocation.getRoom());
                    return locationRepository.save(location);
                })
                .orElseGet(() -> {
                    return locationRepository.save(updatedLocation);
                });
    }

    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }
}
