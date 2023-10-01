package rahulshettyacademy.tests;

import org.testng.annotations.Test;
//import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
//import org.testng.annotations.Test;

import rahulshettyacademy.pageobjects.CartPage;
//import rahulshettyacademy.pageobjects.CheckoutPage;
//import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;
import rahulshettyacademy.testComponents.BaseTest;
import rahulshettyacademy.testComponents.Retry;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {
		loginPage.loginApplication("anshika@gmail.com", "Iamki000");
		Assert.assertEquals("Incorrect email or password.", loginPage.getErrorMessage());
	}
	
	@Test
	@SuppressWarnings("unused")
	public void ProductErrorValidation() throws IOException, InterruptedException {
		String productName = "ZARA COAT 3";
		
		ProductCatalogue productCatalogue = loginPage.loginApplication("rahulshetty@gmail.com", "Iamking@000");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
	}

}
