package com.bartoszswiech.home_inventory_api.repositories;

import com.bartoszswiech.home_inventory_api.beans.Product;
import com.bartoszswiech.home_inventory_api.interfaces.ProductView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<ProductView> findAllBy();
}
