package testng.listeners

import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestResult

class TestNGListener : ITestListener {
    override fun onTestStart(result: ITestResult?) {
    }

    override fun onTestSuccess(result: ITestResult?) {
    }

    override fun onTestFailure(result: ITestResult?) {
    }

    override fun onTestSkipped(result: ITestResult?) {
    }

    override fun onTestFailedButWithinSuccessPercentage(result: ITestResult?) {
    }

    override fun onStart(context: ITestContext?) {
    }

    override fun onFinish(context: ITestContext?) {
    }
}
