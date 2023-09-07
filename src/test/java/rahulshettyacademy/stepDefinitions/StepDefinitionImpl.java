package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LoginPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;
import rahulshettyacademy.testComponents.BaseTest;

public class StepDefinitionImpl extends BaseTest{

	public LoginPage loginPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void landed_on_ecommerce_page() throws IOException {
		loginPage = launchApplication();
		//code
	}
	
	@Given("^I logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) {
		productCatalogue = loginPage.loginApplication(username,password);
	}
	
	@SuppressWarnings("unused")
	@When("^I add product (.+) to Cart$")
	public void add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^I checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) {
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
	}
	
    @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed(String string) {
    	String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
    }
    
    @Then("^\"([^\"]*)\" message is displayed$")
    public void error_message_is_displayed(String strArg1) throws Throwable {
    	Assert.assertEquals(strArg1, loginPage.getErrorMessage());
    	driver.close();
    }
	
}
