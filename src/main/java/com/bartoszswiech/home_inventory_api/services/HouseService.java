package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.HouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Transactional
    public House createHouse(House newHouse) {
        if (newHouse.getId() != null && houseRepository.existsById(newHouse.getId())) {
            throw new EntryAlreadyExistsException(newHouse.getId().toString());
        }
        return houseRepository.save(newHouse);
    }

    public List<House> findAll() {
        return houseRepository.findAll();
    }

    public House findById(Long id) {
        return houseRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));
    }

    @Transactional
    public House update(Long id, House updatedHouse) {
        return houseRepository.findById(id)
                .map(house -> {
                    // Update fields as necessary
                    Set<Room> rooms = updatedHouse.getRooms();
                    house.setRooms(rooms);
                    house.setTitle(updatedHouse.getTitle());
                    return houseRepository.save(house);
                })
                .orElseGet(() -> {
                    return houseRepository.save(updatedHouse);
                });
    }

    public void deleteById(Long id) {
        houseRepository.deleteById(id);
    }
}
