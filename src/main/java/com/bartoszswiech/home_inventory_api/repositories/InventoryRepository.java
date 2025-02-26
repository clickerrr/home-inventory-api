package com.bartoszswiech.home_inventory_api.repositories;

import com.bartoszswiech.home_inventory_api.beans.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
