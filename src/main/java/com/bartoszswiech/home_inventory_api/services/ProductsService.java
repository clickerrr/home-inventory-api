package com.bartoszswiech.home_inventory_api.services;

import com.bartoszswiech.home_inventory_api.beans.NutritionalInformation;
import com.bartoszswiech.home_inventory_api.beans.Product;
import com.bartoszswiech.home_inventory_api.exceptions.EntryAlreadyExistsException;
import com.bartoszswiech.home_inventory_api.exceptions.EntryNotFoundException;
import com.bartoszswiech.home_inventory_api.repositories.NutritionalInformationRepository;
import com.bartoszswiech.home_inventory_api.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {


    private final ProductRepository productRepository;
    private final NutritionalInformationRepository nutritionalInformationRepository;
    ProductsService(ProductRepository productRepository, NutritionalInformationRepository nutritionalInformationRepository) {
        this.productRepository = productRepository;
        this.nutritionalInformationRepository = nutritionalInformationRepository;
    }

    @Transactional
    public Product createProduct(Product newProduct) {
        if(productRepository.findById(newProduct.getUpca()).isPresent()) {
            throw new EntryAlreadyExistsException(newProduct.getUpca());
        }
        // Save the product (which will also save the nutritional information due to cascade)
        return productRepository.save(newProduct);
    }

    public List<Product> findAll() {


        return productRepository.findAll();
    }

    public List<NutritionalInformation> findAllNutritional(){
        return nutritionalInformationRepository.findAll();
    }

    public Product findById(String upca) {
        return productRepository.findById(upca).orElseThrow(() -> new EntryNotFoundException(upca));
    }


    public Product updateProduct(String upca, Product newProduct) {
        return productRepository.findById(upca)
                .map(employee -> {
                    employee.setTitle(newProduct.getTitle());
                    employee.setContainerType(newProduct.getContainerType());
                    return productRepository.save(employee);
                })
                .orElseGet(() -> {
                    return productRepository.save(newProduct);
                });
    }

    public void deleteById(String upca) {
        productRepository.deleteById(upca);
    }
}
