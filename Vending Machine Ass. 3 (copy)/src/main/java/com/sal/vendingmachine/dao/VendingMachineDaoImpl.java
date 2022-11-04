package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import com.sal.vendingmachine.dto.Product;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.nio.file.*;
import java.util.*;

public class VendingMachineDaoImpl implements VendingMachineDao {

    private Map<String ,Product> productsmap =new HashMap<>();
    public final String Product_File;
    private static final String DELIMITER= "::";

    public VendingMachineDaoImpl() {
        Product_File="product1.txt";
//        System.out.println();
//        System.out.println();
//        System.out.println(Paths.get(Product_File));
    }
 public VendingMachineDaoImpl(String ProductTextFile) {
        Product_File=ProductTextFile;
   }

    @Override
    public Map<String, Product> loadProductFromFile() throws VendingMachinePersistenceException {
        Scanner scanner;

        try{
            scanner=new Scanner(new BufferedReader(new FileReader(Product_File)));

        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(" uhm..could not load into memory", e);
        }
        String currentLine;
        Product currentProduct;
        while(scanner.hasNextLine()){
            currentLine= scanner.nextLine();
            currentProduct=new Product(currentLine);
            productsmap.put(currentProduct.getProductId(),currentProduct);
//            String[] currentLineSplitUP= currentLine.split(DELIMITER);
//            String snackproductName= currentLineSplitUP[1];
//            BigDecimal snackproductsCost= new BigDecimal(currentLineSplitUP[2]);
//            int snackproductsID= Integer.parseInt(currentLineSplitUP[0]);
//            int itemsInStock=Integer.parseInt(currentLineSplitUP[3]);
//            Product addNewProducts= new Product(snackproductsID, snackproductName,snackproductsCost,itemsInStock);
//            productsmap.put(snackproductName,addNewProducts);
//            productsmap.put(currentProduct.getProductId(), currentProduct);
        }
        scanner.close();
        return productsmap;
    }

    public void writeProductsToFile() throws VendingMachinePersistenceException{
        PrintWriter out;
        try {
            out=new PrintWriter( new FileWriter(Product_File));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Eh.. Could not save the products data ", e);
        }
        String ProductAsText;
        List<Product> productList=this.getAllProduct();
        for(Product currentProduct:productList)
        {
           // System.out.println(currentProduct.toString());
        }

        for(Product currentProduct: productList){
            ProductAsText=currentProduct.marshalProductAsText();
            out.println(ProductAsText);
            out.flush();
        }
        out.close();
    }

    @Override//so this part is suppose to add the items to the list
    public Product addProduct(String productID, Product item) {
        Product prevProduct=productsmap.put(productID, item);
        return prevProduct;
    }
//now we need a way to get all the items and return them as an array list



    @Override
    public List<Product> getAllProduct() {
        return new ArrayList<Product>(productsmap.values());
    }

    @Override
    public List<String> getAllProductIds() {
        return new ArrayList<>(productsmap.keySet());
    }

    @Override
    public Product getProduct(String productID) {
        return productsmap.get(productID);
    }
    @Override
    public Product removeProduct(String productID){
        Product removeProduct = productsmap.remove(productID);
        return removeProduct;
    }

    @Override
    public Product updateProducts(String productId, Product product) {
        return productsmap.replace(productId, product);
    }


    //  @Override
    // public Product updateProducts(String productId, Product product){
    // return product.replace(productId, product);
}



