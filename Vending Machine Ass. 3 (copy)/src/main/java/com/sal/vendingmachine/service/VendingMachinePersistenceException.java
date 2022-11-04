package com.sal.vendingmachine.service;

import java.io.IOException;

public class VendingMachinePersistenceException extends Throwable {
    public VendingMachinePersistenceException(String message){
        super(message);
    }

    public VendingMachinePersistenceException(String message, Throwable cause){
        super(message,cause);
    }    }

