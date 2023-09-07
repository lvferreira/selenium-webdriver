package rahulshettyacademy.tests;

import java.io.IOException;
//import java.io.File;
import java.util.HashMap;
import java.util.List;
//import java.time.Duration;

//import org.openqa.selenium.By;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

//import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.testComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
//import rahulshettyacademy.pageobjects.LoginPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;


public class SubmitOrderTest extends BaseTest{
	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData",groups= {"Purchase"})
	@SuppressWarnings("unused")
	public void SubmitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		ProductCatalogue productCatalogue = loginPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	
	@Test(dependsOnMethods= {"SubmitOrder"})
	public void OrderHistoryTest()
	{
		//"ZARA COAT 3";
		ProductCatalogue productCatalogue = loginPage.loginApplication("anshika@gmail.com", "Iamking@000");
		OrderPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	//Extent Reports - Data Provider
	@DataProvider
	public Object[][] getData() throws IOException
	{
		String filePath = "//src//test//java//rahulshettyacademy//data//PurchaseOrder.json";
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+ filePath);
		return new Object[][]  { { data.get(0)}, {data.get(1) } };
	}
	
}
