package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.exceptions.UserAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.repositories.UserRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder(10);

    @Transactional
    public boolean createUser(User newUser) throws UserAlreadyExistsException {

        String providedUsername = newUser.getUsername();
        String providedEmail  = newUser.getEmail();

        // TODO: add error checking for no username and email provided. right now we are assuming that a perfect request has been passed.Î
        if(findUser(providedUsername) != null) {
            throw new UserAlreadyExistsException();
        }

        if(providedEmail != null) {
            if (findUserByEmail(providedEmail) != null) {
                throw new UserAlreadyExistsException();
            }
        }
        String encodedPassword = passEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        userRepository.save(newUser);
        return true;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUser(String usernameToFind) {
        return userRepository.findByUsername(usernameToFind);
    }

    public User findUserByEmail(String emailToFind) {
        return userRepository.findByEmail(emailToFind);
    }


    public String verifyUser(User userToVerify) {

            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userToVerify.getUsername(), userToVerify.getPassword()));

            if(authentication.isAuthenticated()) {
                String authToken = jwtService.generateToken(userToVerify.getUsername());
                String refreshToken = jwtService.generateRefreshToken(userToVerify.getUsername());
                JsonObject value = Json.createObjectBuilder()
                        .add("authToken", authToken)
                        .add("refreshToken", refreshToken).build();
                return value.toString();
            }

            return "User not verified";
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

    public String getNewToken() {
        String authedUser = (SecurityContextHolder.getContext().getAuthentication().getName());
        return jwtService.generateToken(authedUser);
    }
}
