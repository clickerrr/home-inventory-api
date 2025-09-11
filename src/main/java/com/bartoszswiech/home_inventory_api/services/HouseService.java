package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.beans.Room;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.exceptions.UserNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.HouseRepository;
import com.bartoszswiech.home_inventory_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class HouseService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HouseRepository houseRepository;

    @Transactional
    public House createHouse(House newHouse) {
        if (newHouse.getId() != null && houseRepository.existsById(newHouse.getId())) {
            throw new EntryAlreadyExistsException(newHouse.getId().toString());
        }
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User associatedUser = userRepository.findByUsername(currentUsername);
        associatedUser.addHouse(newHouse);

        return houseRepository.save(newHouse);
    }


    public List<House> findAll() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<House> foundHouses = userRepository.findByUsername(currentUsername).getHouses();
        System.out.println("!!!!! found houses " + foundHouses.toString());
        List<House> returnList = new ArrayList<House>();

        for(House foundHouse: foundHouses) {
            System.out.println(foundHouse);
            returnList.add(foundHouse);
        }
        return returnList;
    }

    public House findById(Long id) {
        return houseRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));
    }

    public boolean currentUserInHouse(House houseToCheck) throws UserNotFoundException {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User associatedUser = userRepository.findByUsername(currentUsername);
        if(associatedUser == null) {
            throw new UserNotFoundException();
        }
        return houseToCheck.getUsers().contains(associatedUser);
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
