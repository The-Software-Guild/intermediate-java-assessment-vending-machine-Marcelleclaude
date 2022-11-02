package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface VendingMachineServiceLayer{




    Map<String, Product> loadProductsInStock() throws VendingMachinePersistenceException;


    Product getChosenProduct(String productID);

    void checkEnoughMoneyToBuyProduct(BigDecimal amount, Product product) throws InsufficientFundsException;

    void updateProductSale(Product prod) throws NoItemInventoryException, VendingMachinePersistenceException;

    Change calculateChange(BigDecimal amount, Product product);

    void saveProductList() throws VendingMachinePersistenceException;

    void validateProductInStock(String productID) throws NoItemInventoryException;




}
