package com.bartoszswiech.home_inventory_api.repositories;

import com.bartoszswiech.home_inventory_api.beans.User;
import com.bartoszswiech.home_inventory_api.interfaces.ProductView;
import com.bartoszswiech.home_inventory_api.interfaces.UserView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);
    List<UserView> findAllBy();
    UserView findUserViewById(Long id);

}
