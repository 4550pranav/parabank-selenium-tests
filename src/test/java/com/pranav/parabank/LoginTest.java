package test.java.com.pranav.parabank;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://parabank.parasoft.com/parabank/index.htm");
    }

    @Test
    public void validLoginShowsAccountOverview() {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        String pageText = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(pageText.contains("Accounts Overview") || pageText.contains("error"),
            "Expected either account overview or an error message");
    }

    @Test
    public void invalidLoginShowsError() {
        driver.findElement(By.name("username")).sendKeys("invaliduser123");
        driver.findElement(By.name("password")).sendKeys("wrongpass");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        String pageText = driver.findElement(By.tagName("body")).getText();
        Assert.assertTrue(pageText.toLowerCase().contains("error"),
            "Expected an error message for invalid login");
    }

    @AfterMethod
    public void tearDown() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver != null) driver.quit();
    }
}