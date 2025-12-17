package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class LogTestListener implements ITestListener {

    private static final Logger headerLogger = LogManager.getLogger("HeaderLogger");

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        ThreadContext.put("testName", testName);
        headerLogger.info("");
        headerLogger.info("--------  " + testName + "  --------");
        headerLogger.info("");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ThreadContext.remove("testName");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ThreadContext.remove("testName");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ThreadContext.remove("testName");
    }
}
