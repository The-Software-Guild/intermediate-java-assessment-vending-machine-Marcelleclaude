package com.sal.vendingmachine.dto;

import com.sal.vendingmachine.service.VendingMachinePersistenceException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;

//package private is the default
public class Product {
    private String productId;
    private String productName;
   private BigDecimal price;
  private int itemsInStock;

    private final String DELIMITER = "::";
    private int productsInStock;
    //Constructor
    public Product(String productId, String productName, BigDecimal price, int itemsInStock) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.itemsInStock = itemsInStock;
    }

    public Product(String productAsText) throws VendingMachinePersistenceException {
        try {
            String[] fields = productAsText.split(DELIMITER);
            this.productId = fields[0];
            this.productName = fields[1];
            this.price = new BigDecimal(fields[2]);
            this.itemsInStock = Integer.parseInt(fields[3]);
        } catch (PatternSyntaxException ex) {
            throw new VendingMachinePersistenceException(ex.getMessage(), ex);
        } catch (NullPointerException | NumberFormatException e) {
            throw new VendingMachinePersistenceException(e.getMessage(), e);
        }
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //public int getproductsInStock() {
       // return productsInStock;
    //}
    public int getproductsInStock() {
        return itemsInStock;
    }

    public void setproductsInStock(int itemsInStock) {
        this.itemsInStock = itemsInStock;
    }

    @Override
    public int hashCode() {
        //79 is just a number. you can put any number you want
        //how you can tell if a data in one object is = to the data in another object
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.productId);
        hash = 79 * hash + Objects.hashCode(this.productName);
        hash = 79 * hash + Objects.hashCode(this.price);
        hash = 79 * hash + Objects.hashCode(this.itemsInStock);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() !=obj.getClass()){
            return false;
        }
        final Product other = (Product) obj;
        if (this.itemsInStock != other.itemsInStock) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.productName, other.productName)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString (){return "Product{" + "productId=" + productId + ", productName=" + productName + ", price=" + price + ", itemsInStock=" + itemsInStock + '}';
    }

    public String marshalProductAsText() {
        return productId + DELIMITER + productName + DELIMITER + price + DELIMITER + itemsInStock;
    }
}

