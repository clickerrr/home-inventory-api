package com.bartoszswiech.home_inventory_api.repositories;

import com.bartoszswiech.home_inventory_api.beans.House;
import com.bartoszswiech.home_inventory_api.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {

}
