package com.sal.vendingmachine.dao;

import com.sal.vendingmachine.service.VendingMachinePersistenceException;

public interface VendingMachineAuditDao {

    void writeAuditInfo(String info) throws VendingMachinePersistenceException;

}
