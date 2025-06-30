package com.bartoszswiech.home_inventory_api.controllers;

import com.bartoszswiech.home_inventory_api.beans.NutritionalInformation;
import com.bartoszswiech.home_inventory_api.beans.Product;
import com.bartoszswiech.home_inventory_api.interfaces.ProductView;
import com.bartoszswiech.home_inventory_api.services.ProductsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {
    private static final Logger log = LoggerFactory.getLogger(ProductRestController.class);


    ProductsService productsService;
    public ProductRestController(ProductsService productsService) {this.productsService = productsService;}

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/products")
    List<ProductView> all() {
        return productsService.getNecessaryProductInformation();
    }

    @GetMapping("/productsFull")
    List<Product> fullAll() {
        return productsService.findAll();
    }
    // end::get-aggregate-root[]

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/allNutrition")
    List<NutritionalInformation> allNutritionalInformation() {
        return productsService.findAllNutritional();
    }
    // end::get-aggregate-root[]

    @PostMapping("/products")
    Product newProduct(@RequestBody Product newProduct) {
        log.info(newProduct + " added");
        return productsService.createProduct(newProduct);
    }

    @GetMapping("/products/{upca}")
    Product getProduct(@PathVariable String upca) {
        return productsService.findById(upca);
    }

    @PutMapping("/products/{upca}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable String upca) {
        return productsService.updateProduct(upca, newProduct);
    }

    @DeleteMapping("/products/{upca}")
    void deleteProduct(@PathVariable String upca) {
        productsService.deleteById(upca);

    }
}
