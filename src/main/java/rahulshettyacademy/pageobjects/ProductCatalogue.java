package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.abstractcomponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;

    public ProductCatalogue(WebDriver driver) {
        // Initialization
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Locators using @FindBy
    @FindBy(css = ".mb-3")
    private List<WebElement> products;

    @FindBy(css = ".ng-animating")
    private WebElement spinner;

    // Locators using By
    private By productsBy = By.cssSelector(".mb-3");
    private By addToCart = By.cssSelector(".card-body button:last-of-type");
    private By toastMessage = By.cssSelector("#toast-container");

    // Get the list of products on the page.
    public List<WebElement> getProductList() {
        waitForElementToAppear(productsBy);
        return products;
    }
    
    // Get a product element by its name.
    public WebElement getProductByName(String productName) {
        return getProductList().stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst()
                .orElse(null);
    }

    // Add a product to the cart by name.
    public void addProductToCart(String productName) throws InterruptedException {
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(spinner);
    }
}
