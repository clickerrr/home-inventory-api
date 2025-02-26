package com.bartoszswiech.home_inventory_api.repositories;

import com.bartoszswiech.home_inventory_api.beans.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

}
