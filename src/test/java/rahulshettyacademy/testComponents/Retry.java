package rahulshettyacademy.testComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

    private int count = 0; // Tracks the number of retries
    private final int maxTry = 1; // Maximum number of retries allowed

    @Override
    public boolean retry(ITestResult result) {
        // Check if the maximum number of retries has been reached
        if (count < maxTry) {
            count++; // Increment the retry count
            return true; // Retry the test
        }
        return false; // Do not retry the test further
    }
}
