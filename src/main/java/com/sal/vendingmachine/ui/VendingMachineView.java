package com.sal.vendingmachine.ui;

import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.CoinValue;
import com.sal.vendingmachine.dto.Product;

import java.math.BigDecimal;
import java.util.List;

//this class hanfle all interaction with our vending machine
public class VendingMachineView {
    private static UserIO io;


    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public static void displayVendingMachineMenu() {
        System.out.println("Vending Machine Menu");
        io.print("1.Add Money");
        io.print("2. Buy Item");
        io.print("3.Quit");

    }

    public void displayVendingMachineWelcome(){
        io.print("Welcome");
    }
    public void displayProductHeader(){
        io.print("mo\tProduct\t\tPrice");
        io.print("====================");
    }
    public void displayItem(Product itemprod){
        io.print(itemprod.getProductId() + "\t" + itemprod.getProductName() + "\t\t" + itemprod.getPrice());
    }
    public BigDecimal promptUserMoneyInput(){
        return io.readBigDecimal("\nPlease put in money: ");
    }
    public String promptUserItemChoice(){
        return io.readString("Please choose the product you want (Enter a No)");
    }
    public void displayUserChoiceOfProduct(Product itemProduct){
        io.print("You have chosen " + itemProduct.getProductName() + ".");
    }
    public void displayUserMoneyInput(BigDecimal amount) {
        io.print("You have deposited $" + amount + "..");
    }
    public void displayChangeDue(Change change){
        io.print("The change due: ");
        io.print(CoinValue.QUARTERS + " : " + change.getQuarters());
        io.print(CoinValue.DIMES + " : " + change.getDimes());
        io.print(CoinValue.NICKELS + " : " + change.getNickels());
        io.print(CoinValue.PENNIES + " : " + change.getPennies());
    }

//    public void displayAllItems(List<Product> Products) {
//    }

    //other methods  of how you want your user to interact with vending machine
    //example"getting the users choice
    // use a prompt to get what they want to do.
    public static String getUserSelection() {
        return io.readString("Please select an item");
    }

    public void displayErrorMessage(String errorMessage) {
        io.print("===ERROR");
        io.print(errorMessage);
    }
    public boolean toExit(){
        String answer = io.readString("Do you want to exit? (y/n) ").toLowerCase();
        if (answer.startsWith("y")){
            return true;
        } else{
            return false;
        }
    }
    public void displayFinalMessage(){
        io.print("Thank you! Come back again");
    }
    public void displayUserResponse(){
        io.print("Do you want to make another selection?");
    }

    public void displayProduct(Product p) {
    }
}
