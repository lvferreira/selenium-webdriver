package rahulshettyacademy.abstractcomponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.OrderPage;

public class AbstractComponent {

    private final WebDriver driver;
    private static final int timeoutInSeconds = 5;

    // Constructor to initialize the driver
    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Web elements
    @FindBy(css = "[routerlink*='cart']")
    private WebElement cartLink;

    @FindBy(css = "[routerlink*='myorders']")
    private WebElement orderLink;

    // Wait for an element with the specified locator to appear
    public void waitForElementToAppear(By findBy) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    // Wait for a WebElement to appear
    public void waitForWebElementToAppear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Navigate to the Cart page and return a CartPage object
    public CartPage goToCartPage() {
    	cartLink.click();
        return new CartPage(driver);
    }

    // Navigate to the Orders page and return an OrderPage object
    public OrderPage goToOrdersPage() {
    	orderLink.click();
        return new OrderPage(driver);
    }

    // Wait for an element to disappear
    public void waitForElementToDisappear(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }
}
