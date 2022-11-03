package TEST;

import com.sal.vendingmachine.dao.VendingMachineDao;
import com.sal.vendingmachine.dao.VendingMachineDaoImpl;
import com.sal.vendingmachine.dto.Product;
import com.sal.vendingmachine.service.VendingMachinePersistenceException;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class VendingMachineDaoImplTest {
    VendingMachineDao testDao;
public VendingMachineDaoImplTest() {

}
    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
   public void setUp() throws VendingMachinePersistenceException, IOException {
            String testFile="testProduct.txt";
            //use the FileWriter to quickly blank the file.
            new FileWriter(testFile);
            testDao=new VendingMachineDaoImpl(testFile);
    }


    @AfterEach
    void tearDown() {
    }
//Methods for texting
    @Test
   public void testAddProduct() {
    //create method test input
        System.out.println("AddProduct");
        BigDecimal cost =new BigDecimal(1.00);
        String ProductID="1";
        Product product = new Product(ProductID,"snickers bar",cost,2);
        product.setProductName("snickers bar");
        product.setPrice(cost);
        product.setproductsInStock(2);
        //Add the Product to the DAO
        testDao.addProduct(ProductID, product);
        //get the product from the DAO
        Product retrieveProduct = testDao.getProduct(ProductID);
        //check if the data is equal
        assertEquals(product.getProductId(), retrieveProduct.getProductId(), "check product id.");
        assertEquals(product.getProductName(),retrieveProduct.getProductName(),"check product name.");
        assertEquals(product.getPrice(),retrieveProduct.getPrice(),"check product price.");
        assertEquals(product.getproductsInStock(),retrieveProduct.getproductsInStock(),"check product in stock.");
    }

    @Test
    void testAddGetAllProduct() {
    //create our first product
        System.out.println("AddGetAllProduct");
        BigDecimal cost=new BigDecimal("1.00");
        Product firstProduct = new Product("1","Snickers bar",cost,2);
        firstProduct.setProductName("Snickers bar");
        firstProduct.setPrice(cost);
        firstProduct.setproductsInStock(2);

        //now we have to create our second item
        BigDecimal price= new BigDecimal("1.99");
        Product secondProduct = new Product("2","CocaCola",price,8);
        secondProduct.setProductName("CocaCola");
        secondProduct.setPrice(price);
        secondProduct.setproductsInStock(8);

        //Add both our products to the DAO
        testDao.addProduct(firstProduct.getProductId(),firstProduct);
        testDao.addProduct(secondProduct.getProductId(), secondProduct);

        //Retrieve the list of all Products within the DAO
        List<Product> allProduct =testDao.getAllProduct();

        // First check the general contents of the list
        assertNotNull(allProduct, "the list of product must not null");
        assertEquals(2,allProduct.size(),"List of products should have 2 products.");

        //Then the Specifics
        assertTrue(testDao.getAllProduct().contains(firstProduct), "the list of products should include Snickers bar.");
        assertTrue(testDao.getAllProduct().contains(secondProduct),"the list of products should include CocaCola.");

    }
    @Test
    void testRemoveProduct(){
    //Create 2 new products
        System.out.println("removeProduct");
        BigDecimal cost = new BigDecimal("1.00");
        Product firstProduct = new Product("1","Snickers bar",cost,2);
        firstProduct.setProductName("Snickers bar");
        firstProduct.setPrice(cost);
        firstProduct.setproductsInStock(2);

        //now we have to create our second item
        BigDecimal price= new BigDecimal("1.99");
        Product secondProduct = new Product("2","CocaCola",price,8);
        secondProduct.setProductName("CocaCola");
        secondProduct.setPrice(price);
        secondProduct.setproductsInStock(8);

        //Add both our products to the DAO
        testDao.addProduct(firstProduct.getProductId(),firstProduct);
        testDao.addProduct(secondProduct.getProductId(), secondProduct);

        //remove the first product-Snickers bar
        Product removedProduct = testDao.removeProduct(firstProduct.getProductId());

        //check that the correct product has been removed
        assertEquals(removedProduct,firstProduct,"the removed product should be Snickers bar");

        //Get all the products
        List<Product> allProducts=testDao.getAllProduct();

        //First check the general content of the list
        assertNotNull(allProducts,"All products should not be null");
        assertEquals(1,allProducts.size(),"All products should only have 1 product.");

        //Then the specifics
        assertFalse(allProducts.contains(firstProduct), "All products should not include Snickers bar");
        assertTrue(allProducts.contains(secondProduct),"All products should not include CocaCola");

        //Remove the second product
        removedProduct=testDao.removeProduct(secondProduct.getProductId());

        //Check that the correct product has been removed
        assertEquals(removedProduct,secondProduct,"the removed product should be CocaCola");

        //retrieve all the products again,and check the list
        allProducts=testDao.getAllProduct();

        //Check the content of the list-it should be empty
        assertTrue(allProducts.isEmpty(),"the retrieved list of products should be empty");

        //try to get both products by their old id- they should be null
        Product retrieveProduct=testDao.getProduct(firstProduct.getProductId());
        assertNull(retrieveProduct,"Snickers bar was removed, should be null");

        retrieveProduct=testDao.getProduct(secondProduct.getProductId());
        assertNull(retrieveProduct,"CocaCola was removed,should be null");
    }
    @Test
    void testUpdateProducts() {
        System.out.println("updateProduct");
        BigDecimal bd = new BigDecimal(2.50);
        Product P1= new Product("1","cake",bd,10);
        bd = new BigDecimal("1.05");
        testDao.addProduct(P1.getProductId(),P1);
        P1.setProductName("water");
        P1.setPrice(bd);
        P1.setproductsInStock(12);
        testDao.updateProducts(P1.getProductId(), P1);
        //result

        Product result= testDao.updateProducts(P1.getProductId(), P1);

        //expected result

        Product expectedResult= new Product("1","water",bd,12);

        //assert
        assertEquals(expectedResult,result,"updated item is water.");
        assertEquals("1",result.getProductId(),"updated product id is 1");
        assertEquals("water",result.getProductName(),"updated product name");
        assertEquals(bd,result.getPrice(),"updated product price is 1.05");
        assertEquals(12,result.getproductsInStock(),"updateItem in stock");
    }
    @Test
    void getAllProductIds() {
    }

    @Test
    void getProduct() {
    }




}