package com.sal.vendingmachine.controller;

import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Product;
import com.sal.vendingmachine.service.InsufficientFundsException;
import com.sal.vendingmachine.service.NoItemInventoryException;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import com.sal.vendingmachine.service.VendingMachineServiceLayer;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineController {
    private UserIO io=new UserIOImpl();
    private VendingMachineView view;
    private VendingMachineServiceLayer service;



    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }


    public void run() throws VendingMachinePersistenceException {
       //view.displayVendingMachineMenu();
      //  String UserSelection =view.getUserSelection();
//        Map<String, Product> productsInStock= service.loadProductsInStock();

        BigDecimal amount = new BigDecimal("0");
        Product ChosenProduct;
        String keepGoing = "yes";
        String input;
        Scanner sc = new Scanner(System.in);
        while (keepGoing.equals("yes")) {
            boolean isEnoughMoney = false;

            try {
                displayHeader();

                //do {
                    productMenu();//it's suppose to print items
                    amount = userMoneyEntry(amount);
                    ChosenProduct = getChosenProduct();
                    isEnoughMoney = didUserPutenoughMoney(amount, ChosenProduct);
                    if (toExit(isEnoughMoney)) {
                        return;
                    }
                //} while (!isEnoughMoney);
                displayUserMoneyEntry(amount);
                displayChangeReturnedToUser(amount, ChosenProduct);
                //System.out.println("This is the product :" + ChosenProduct.toString());
                updateSoldProduct(ChosenProduct);
                saveProductList();


            } catch (VendingMachinePersistenceException | InsufficientFundsException e) {
                displayErrorMessage(e.getMessage());
            } finally {
                displayFinalMessage();
            }
            view.displayUserResponse();
            keepGoing = sc.nextLine();
        }
}

    private void displayErrorMessage(String message) {
        view.displayErrorMessage(message);
    }

    void displayHeader() {
        view.displayVendingMachineWelcome();
    }


    private void productMenu() throws VendingMachinePersistenceException {
        try {
            view.displayProductHeader();
            for (Product p: service.loadProductsInStock().values()){
                view.displayItem(p);
            }
    }catch (VendingMachinePersistenceException e){
            throw new VendingMachinePersistenceException(e.getMessage());
        }
    }

    private BigDecimal userMoneyEntry(BigDecimal amount) {
        //this is resetting amount to be zero.
        amount=BigDecimal.ZERO;
        return amount.add(view.promptUserMoneyInput());
    }

  private Product getChosenProduct()
  {
            String productId= view.promptUserItemChoice();
            Product itemProduct = service.getChosenProduct(productId);
            view.displayUserChoiceOfProduct(itemProduct);

            return itemProduct;

    }

    private boolean didUserPutenoughMoney(BigDecimal amount, Product chosenProduct) throws InsufficientFundsException {
        service.checkEnoughMoneyToBuyProduct(amount,chosenProduct);
        return true;
    }

    private boolean toExit(boolean isEnoughMoney) {
        if (isEnoughMoney){
            return false;
        }else{
            return view.toExit();
        }
    }


    private void displayUserMoneyEntry(BigDecimal amount) {
        view.displayUserMoneyInput(amount);
    }

    private void displayChangeReturnedToUser(BigDecimal amount, Product chosenProduct) {
        Change changes=service.calculateChange(amount, chosenProduct);
        view.displayChangeDue(changes);
    }

    private void updateSoldProduct(Product prod) throws VendingMachinePersistenceException {
        try {
            service.updateProductSale(prod);
        } catch (NoItemInventoryException ex) {
            throw new VendingMachinePersistenceException(ex.getMessage());
        }
    } void saveProductList() throws VendingMachinePersistenceException {
        service.saveProductList();
    }
    private void displayFinalMessage() {
        view.displayFinalMessage();
    }


}


