package com.sal.vendingmachine.dto;

import com.sal.vendingmachine.service.InsufficientFundsException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;

/**
 *
 *
 */
public class Change {
    private int quarters;
    private int dimes;
    private int nickels;
    private int pennies;

    public Change(BigDecimal amount) {
        System.out.println("the amount requested is " + amount + "," + amount.divide(new BigDecimal(".25")));
        this.quarters = amount.divide(new BigDecimal(".25")).intValue();
        amount = amount.remainder(new BigDecimal(".25"));
        this.dimes = amount.divide(new BigDecimal(".10")).intValue();
        amount = amount.remainder(new BigDecimal(".10"));
        this.nickels = amount.divide(new BigDecimal(".05")).intValue();
        amount = amount.remainder(new BigDecimal(".05"));
        this.pennies = amount.divide(new BigDecimal(".01")).intValue();
    }


    public int getQuarters(){
        return quarters;
    }
    public int getDimes(){
        return dimes;
    }
    public int getNickels(){
        return nickels;
    }
    public int getPennies(){
        return pennies;
    }

}
