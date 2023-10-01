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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LoginPage;

public class BaseTest {

    public WebDriver driver;
    public LoginPage loginPage;

    // Constants
    private static final String GLOBAL_DATA_FILE = "//src//main//java//rahulshettyacademy//resources//GlobalData.properties";
    private static final String SCREENSHOT_PATH = "//reports//";

    // Initialize WebDriver for the specified browser
    public WebDriver initializeDriver() throws IOException {
        Properties prop = new Properties();
        FileInputStream fileStream = new FileInputStream(GLOBAL_DATA_FILE);
        prop.load(fileStream);

        String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
                : prop.getProperty("browser");

        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();

        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440, 900));
        } else if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new FirefoxDriver(options);
        } else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (browserName.contains("headless")) {
                options.addArguments("headless");
            }
            driver = new EdgeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    // Read JSON data to a list of hashmaps
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent,
                new TypeReference<List<HashMap<String, String>>>() {});
        return data;
    }

    // Capture a screenshot and save it to the reports directory
    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + SCREENSHOT_PATH + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + SCREENSHOT_PATH + testCaseName + ".png";
    }

    // Set up WebDriver and launch the application
    @BeforeMethod(alwaysRun = true)
    public LoginPage launchApplication() {
        try {
            driver = initializeDriver();
        } catch (IOException e) {
            e.printStackTrace();
        }
        loginPage = new LoginPage(driver);
        loginPage.goTo();
        return loginPage;
    }

    // Close the WebDriver after each test method
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }
}
