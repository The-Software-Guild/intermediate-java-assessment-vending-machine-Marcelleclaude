package TEST;

import com.sal.vendingmachine.dao.VendingMachineAuditDao;
import com.sal.vendingmachine.dao.VendingMachineAuditDaoImpl;
import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dto.Change;
import com.sal.vendingmachine.dto.Product;
import com.sal.vendingmachine.service.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineServiceImplTest {
    VendingMachineDao testDao = new VendingMachineDaoImpl("VendingMachineTestFile.txt");
    String testAuditFile = "testProduct.txt";
    VendingMachineAuditDao testAuditDao = new VendingMachineAuditDaoImpl ();
    VendingMachineServiceLayer testService = new VendingMachineServiceImpl(testAuditDao,testDao);


    public VendingMachineServiceImplTest() throws VendingMachinePersistenceException {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCheckEnoughMoneyToBuyProduct() {
        System.out.println("checkEnoughMoneyToBuyProduct");
        //Arrange
      BigDecimal price = new BigDecimal("1.99");
        Product product = new Product("2","CocaCola",price,8);

        product.setPrice(new BigDecimal(1.99));
        product.setproductsInStock(8);

        BigDecimal enoughMoney = new BigDecimal("2.00");
        BigDecimal notEnoughMoney = new BigDecimal("1.59");

        //act
        try {
            testService.checkEnoughMoneyToBuyProduct(enoughMoney,product);
        }catch (InsufficientFundsException e){
            fail("there is sufficient funds, exception should not have been thrown");
        }
        //act
        try{
            testService.checkEnoughMoneyToBuyProduct(enoughMoney,product);

        }catch (InsufficientFundsException e){
            fail("There insufficient funds, exception should have been thrown");
        }
    }

    @Test
    void testGetChosenProduct() throws VendingMachinePersistenceException {
        System.out.println("getChosenProduct");
        Product expectedResult = new Product("1");
        Product result = new Product("1");
        assertEquals(expectedResult,result,"check both items equals");
        assertEquals(expectedResult.getProductName(),result.getProductName(), "check both items");


    }

    @Test
    void testValidateProductInStock(){
        System.out.println("ValidateProductInStock");
        String productID = "2";
        BigDecimal price = new BigDecimal(1.99);
        Product P2 = new Product(productID,"CocaCola",price,0);
        P2.setProductName("CocaCola");
        P2.setPrice(price);
        P2.setproductsInStock(0);

        try {
            testService.validateProductInStock(productID);
            fail("Selected Item is not in stock");
        } catch (NoItemInventoryException e){
    }
    }

    @Test
    void testCalculateChange () throws VendingMachinePersistenceException {
        System.out.println("CalculateChange");
        //ACt
        String productId="1";
        String productName="cake";
        BigDecimal price =new BigDecimal("1.99");
        int itemsInStock=8;

        Product aProduct= new Product(productId, productName, price,itemsInStock);
        BigDecimal amount = new BigDecimal("2.90");
        Change result = testService.calculateChange(amount, aProduct);

        //assert
        assertEquals(3, result.getQuarters(), "Check number of quarters");
        assertEquals(1, result.getDimes(), "Check number of dimes");
        assertEquals(1, result.getNickels(), "Check number of nickels");
        assertEquals(1, result.getPennies(), "Check number of pennies");

    }
    @Test
    void testUpdateProductSale() throws NoItemInventoryException, VendingMachinePersistenceException {
        System.out.println("UpdateProductSale");
        Product product1 = testService.getChosenProduct("1");
        //test if items in stock
        assertEquals(9,product1.getproductsInStock(), "check if product is in stock");
        testService.updateProductSale(product1);
        Product updateProduct = testService.getChosenProduct("1");
        assertEquals(8,updateProduct.getproductsInStock(),"check items in stock");

    }
}
