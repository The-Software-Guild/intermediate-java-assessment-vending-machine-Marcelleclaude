package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.dto.Product;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;

import java.util.List;
import java.util.Map;

public interface VendingMachineDao {
    Map<String, Product> loadProductFromFile() throws VendingMachinePersistenceException;

    Product addProduct(String productId, Product product);

    List<Product> getAllProduct();

    List<String> getAllProductIds();

    Product getProduct(String productID);

    Product removeProduct(String productID);

    Product updateProducts(String productId, Product product);
    void writeProductsToFile() throws VendingMachinePersistenceException;
}
