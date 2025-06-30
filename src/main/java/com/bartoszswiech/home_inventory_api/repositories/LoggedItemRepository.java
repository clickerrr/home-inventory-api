package com.bartoszswiech.home_inventory_api.repositories;

import com.bartoszswiech.home_inventory_api.beans.LoggedItem;
import com.bartoszswiech.home_inventory_api.interfaces.LoggedItemWithProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoggedItemRepository extends JpaRepository<LoggedItem, Long> {
    @Query("select loggedItem from LoggedItem loggedItem where loggedItem.id = ?1")
    LoggedItemWithProduct findLoggedItemWithProduct(Long id);
}
