package rahulshettyacademy.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

import rahulshettyacademy.pageobjects.LoginPage;

public class BaseTest {

	public WebDriver driver;
	public LoginPage loginPage;

	public WebDriver initializeDriver() throws IOException {
		// Properties Object/Class Instance
		String globalData = System.getProperty("user.dir")
				+ "//src//main//java//rahulshettyacademy//resources//GlobalData.properties";
		FileInputStream fileStream = new FileInputStream(globalData);

		Properties prop = new Properties();
		prop.load(fileStream);

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			// full-screen
			driver.manage().window().setSize(new Dimension(1440, 900));
		} else if (browserName.equalsIgnoreCase("firefox")) {
			// Firefox
			FirefoxOptions options = new FirefoxOptions();
			// System.setProperty("webdriver.gecko.driver", "geckodriver.exe");
			WebDriverManager.firefoxdriver().setup();
			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			// Edge
			EdgeOptions options = new EdgeOptions();
			// System.setProperty("webdriver.edge.driver", "msedgedriver.exe");
			WebDriverManager.edgedriver().setup();
			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		// Read JSON To String
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		// String To HashMap - Jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
		// {map, map}
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
	}

	@BeforeMethod(alwaysRun = true)
	public LoginPage launchApplication() {
		try {
			driver = initializeDriver();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginPage = new LoginPage(driver);
		loginPage.goTo();
		return loginPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.close();
	}
}
