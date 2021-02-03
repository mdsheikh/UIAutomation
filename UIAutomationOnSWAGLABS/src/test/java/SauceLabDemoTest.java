import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SauceLabDemoTest {

    WebDriver driver;

    // setting up the driver
    @BeforeTest
    public void setup(){

        String exePath = "C:\\Users\\mdshe\\Downloads\\chromedriver_win32\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", exePath);
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        Reporter.log("--- Browser Opened successfully...");
    }

    // login to the site
    @Test
    public void test1_login(){

        driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//*[@id='login-button']")).click();
        String expectedTitle = "Swag Labs";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
        Reporter.log("---Test Completed...");
    }

    // sort and print the prices
    @Test
    public void test2_sortItems(){

        List<String> prices = new ArrayList<String>();
        List<WebElement> listItem = driver.findElements(By.xpath("//*[contains(@class, 'inventory_item_price')]"));
        for(int i = 0; i < listItem.size(); i++){
            prices.add(listItem.get(i).getText());
        }

        // need to work on sorting
        Collections.sort(prices);

        System.out.println("Total items: " + prices.size());  //

        //printing all the prices in a sorted order
        for(String i : prices) {
            System.out.println(i);

        }

        Assert.assertEquals(6, prices.size());
        Reporter.log("---Test Completed...");
    }

    // add 3 items in the cart
    @Test
    public void test3_addItems(){

        driver.findElement(By.xpath("(//*[contains(@class, 'btn_primary btn_inventory')])[1]")).click();
        driver.findElement(By.xpath("(//*[contains(@class, 'btn_primary btn_inventory')])[2]")).click();
        driver.findElement(By.xpath("(//*[contains(@class, 'btn_primary btn_inventory')])[3]")).click();
        String cartNumber = driver.findElement(By.xpath("//*[@class='fa-layers-counter shopping_cart_badge']")).getText();
        System.out.println("Items in the cart: " + cartNumber);

        Assert.assertEquals(cartNumber, "3", "msessage passed");
        System.out.println(" 3 items were added......");
        Reporter.log("---Test Completed...");

    }

    // checking the added items in the cart
    @Test
    public void test4_checkItemsInCart(){

        List<WebElement> elementsInCarts = driver.findElements(By.xpath("(//*[contains(@class , 'btn_secondary btn_inventory')])"));
        int size = elementsInCarts.size();
        Assert.assertEquals(3, size);
        System.out.println(" 3 items added in the cart successfully.....");
        Reporter.log("---Test Completed...");
    }

    //remove an item from the cart
    @Test
    public void test5_removeItemFromCart(){
        driver.findElement(By.xpath("(//*[contains(@class , 'btn_secondary btn_inventory')])[1]")).click();
        List<WebElement> elementsInCarts = driver.findElements(By.xpath("(//*[contains(@class , 'btn_secondary btn_inventory')])"));
        int size = elementsInCarts.size();
        Assert.assertEquals(2, size);
        System.out.println("an item were removed from the cart.....");
        Reporter.log("---Test Completed...");
    }

    // add another item tin the cart
    @Test
    public void test6_addAnotherItemToCart(){
        driver.findElement(By.xpath("(//*[contains(@class, 'btn_primary btn_inventory')])[4]")).click();
        List<WebElement> elementsInCarts = driver.findElements(By.xpath("(//*[contains(@class , 'btn_secondary btn_inventory')])"));
        int size = elementsInCarts.size();
        Assert.assertEquals(3, size);
        System.out.println("Another item were added in the cart......");
        Reporter.log("---Test Completed...");
    }

    //complete check out after checking the total price, items...
    @Test
    public void test7_checkOut(){

        driver.findElement(By.xpath("//*[@class='fa-layers-counter shopping_cart_badge']")).click();

        driver.findElement(By.xpath("//*[@class='btn_action checkout_button']")).click();

        driver.findElement(By.xpath("//*[@id='first-name']")).sendKeys("Robert");
        driver.findElement(By.xpath("//*[@id='last-name']")).sendKeys("khan");
        driver.findElement(By.xpath("//*[@id='postal-code']")).sendKeys("10001");

        driver.findElement(By.xpath("//*[@type='submit']")).click();

        String totalPrice = driver.findElement(By.xpath("//*[@class='summary_total_label']")).getText();
        System.out.println("Total price is " + totalPrice);

        Assert.assertEquals("Total: $43.17",totalPrice );   // asserting total price

        driver.findElement(By.xpath("//*[@class='btn_action cart_button']")).click();
        String finishTest = driver.findElement(By.xpath("//*[@class='subheader']")).getText();

        Assert.assertEquals("Finish",finishTest);

        System.out.println("--- checkout is done successfully");
        Reporter.log("--- Test Completed...");

    }


    // closing the driver
    @AfterTest
    public void close(){

        driver.close();
    }


}
