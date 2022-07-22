package testng.retry

import org.testng.IRetryAnalyzer
import org.testng.ITestResult
import util.PropertiesHelper

class Retry : IRetryAnalyzer {
    private var retryCount = 0

    override fun retry(result: ITestResult): Boolean {
        if (retryCount < maxRetryCount) {
            retryCount++
            return true
        }
        return false
    }

    companion object {
        private val maxRetryCount = PropertiesHelper().getProperty("maxRetryCount", "0").toInt()
    }
}
