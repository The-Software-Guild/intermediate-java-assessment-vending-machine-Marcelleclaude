package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.service.VendingMachinePersistenceException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class VendingMachineAuditDaoImpl implements VendingMachineAuditDao {
    public static final String Audit_File = "audit.txt";


    @Override
    public void writeAuditInfo(String info) throws VendingMachinePersistenceException {
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(Audit_File, true));

        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not put in audit information", e);
        }
        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + "::" + info);
        out.flush();
        out.close();
    }
}





