package com.sal.Vendingmachine;

import com.sal.vendingmachine.controller.VendingMachineController;
import com.sal.vendingmachine.dao.VendingMachineAuditDao;
import com.sal.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import com.sal.vendingmachine.service.VendingMachineServiceImpl;
import com.sal.vendingmachine.service.VendingMachineServiceLayer;
import com.sal.vendingmachine.ui.UserIO;
import com.sal.vendingmachine.ui.UserIOImpl;
import com.sal.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws VendingMachinePersistenceException {
//            UserIO io = new UserIOImpl();
//            //make a vending machine object to allow interaction with our vending machine
//            //vending machine object needs the user IO object in constructor to create it
//            //now we can call our different Vendingmachine methods
//            VendingMachineView view = new VendingMachineView(io);
//            VendingMachineDao dao =new VendingMachineDaoImpl();
//            VendingMachineAuditDao auditDao = new VendingMachineAuditDaoImpl();
//            VendingMachineServiceLayer service =new VendingMachineServiceImpl(auditDao, dao);
//
//            VendingMachineController controller= new VendingMachineController(view, service);
          //  controller.run();


        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller =
                ctx.getBean("controller", VendingMachineController.class);
        controller.run();

    }
}