package com.bartoszswiech.home_inventory_api.repositories;

import com.bartoszswiech.home_inventory_api.beans.LoggedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggedItemRepository extends JpaRepository<LoggedItem, Long> {
}
