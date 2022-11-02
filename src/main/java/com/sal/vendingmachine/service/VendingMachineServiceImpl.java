package com.sal.vendingmachine.service;

import com.sal.vendingmachine.dao.VendingMachineAuditDao;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Product;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VendingMachineServiceImpl implements VendingMachineServiceLayer{
    private final VendingMachineDao dao;
    private VendingMachineAuditDao auditdao;
    public VendingMachineServiceImpl(VendingMachineAuditDao auditDao, VendingMachineDao dao) {
        this.dao = dao;
     this.auditdao = auditDao;
    }
    //The service layer is responsible for the business logic of an application. It sits between
    //the controller and DAOs.

    @Override
    public Map<String, Product> loadProductsInStock() throws VendingMachinePersistenceException {
        Map<String, Product> productsInStock = new HashMap();
        //Iterator var3 = dao.loadProductFromFile().values().iterator();
        for(Product p: dao.loadProductFromFile().values()){
            if(p.getproductsInStock() <1){

             //   auditdao.writeAuditInfo("Product Id: " + p.getProductId() + "quantity in stock is low");
            }else{
                productsInStock.put(p.getProductId(), p);
            }
        }

//        while(var3.hasNext()) {
//            Product product = (Product) var3.next();
//            if (product.getproductsInStock() < 1) {
//               auditdao.writeauditInfo("Product Id: " + product.getProductId() + "quantity in stock is low");
//            } else
//                productsInStock.put(product.getProductId(), product);
//        }
        return productsInStock;
    }

    @Override
    public Product getChosenProduct(String productID) {
        try {
            this.validateProductInStock(productID);
        } catch (NoItemInventoryException var3) {
            Logger.getLogger(VendingMachineServiceImpl.class.getName()).log(Level.SEVERE, (String)null, var3);
        }

        return this.dao.getProduct(productID);
    }

    @Override
    public void checkEnoughMoneyToBuyProduct(BigDecimal amount, Product itemproduct) throws InsufficientFundsException {
        if (amount.compareTo(itemproduct.getPrice()) < 0) {

                throw new InsufficientFundsException("Insufficient funds to buy" + itemproduct.getProductName());

            }
        }


    @Override
    public void updateProductSale(Product prod) throws NoItemInventoryException, VendingMachinePersistenceException {
        if (prod.getproductsInStock() > 0) {
            prod.setproductsInStock(prod.getproductsInStock() - 1);
//            this.dao.updateProducts(prod.getProductId(), prod);

//            try {
//                this.auditdao.writeAuditInfo("Item Id: " + prod.getProductId() + "is updated");
//            } catch (VendingMachinePersistenceException var3) {
//                Logger.getLogger(VendingMachineServiceImpl.class.getName()).log(Level.SEVERE, (String)null, var3);
//            }
//
        } else {
            throw new NoItemInventoryException(" Selected Item is not in stock");
        }
        System.out.println("This is the product we are subtracting from: "+prod.toString());
        dao.updateProducts(prod.getProductId(), prod);
        auditdao.writeAuditInfo("Item: " + prod.getProductName() + " is updated. Inventory is " + prod.getproductsInStock());

    }

    @Override
    public Change calculateChange(BigDecimal amount, Product chosenProduct) {
        BigDecimal change = amount.subtract(chosenProduct.getPrice());
        return new Change(change);
    }

    @Override
    public void saveProductList() throws VendingMachinePersistenceException {
        this.dao.writeProductsToFile();
       //this.auditdao.writeAuditInfo("Item list saved to file." + dao.);
    }

    @Override
    public void validateProductInStock(String productID) throws NoItemInventoryException {
        List<String> ids = this.dao.getAllProductIds();
        Product produit = this.dao.getProduct(productID);
        if (!ids.contains(productID) || produit.getproductsInStock() < 1) {
            throw new NoItemInventoryException("Selected Item is not in stock");

        }

    }}