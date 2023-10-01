package rahulshettyacademy.testComponents;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {
    
    private ExtentReports extent = ExtentReporterNG.getReportObject();
    private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); // Thread-safe

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test in the Extent report
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); // Set the thread-local test instance
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log test success in the Extent report
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log test failure in the Extent report and capture a screenshot
        extentTest.get().fail(result.getThrowable());
        WebDriver driver = null;
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String filePath = getScreenshot(result.getMethod().getMethodName(), driver);
            extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Test skipped, no specific actions needed
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Test failed within success percentage, no specific actions needed
    }

    @Override
    public void onStart(ITestContext context) {
        // Test suite execution starts, no specific actions needed
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the Extent report to save the results
        extent.flush();
    }
}
