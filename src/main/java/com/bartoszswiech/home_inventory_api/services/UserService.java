package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(User newUser) {
        if (newUser.getId() != null && userRepository.existsById(newUser.getId())) {
            throw new EntryAlreadyExistsException(newUser.getId().toString());
        }

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        newUser.setEncodedPassword(passwordEncoder.encode(newUser.getEncodedPassword()));
        return userRepository.save(newUser);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean verifyUser(String email, String inputtedPassword) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        User returnedUser = userRepository.findByEmail(email);
        if(returnedUser == null) {
            throw new EntryNotFoundException("User does not exist");
        }

//        return passwordEncoder.matches(inputtedPassword, returnedUser.getEncodedPassword());
        return false;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntryNotFoundException(id.toString()));
    }

    @Transactional
    public User update(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    // Update fields as necessary
                    Set<House> houses = updatedUser.getHouses();
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());


                    return userRepository.save(user);
                })
                .orElseThrow(() -> {
                    return new EntryNotFoundException("User not found");
                });
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
