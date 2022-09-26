package testng.retry

import com.github.qky666.selenidepom.data.PropertiesHelper
import org.testng.IRetryAnalyzer
import org.testng.ITestResult

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
        private val maxRetryCount =
            PropertiesHelper(listOf("project.properties")).getProperty("maxRetryCount", "0").toInt()
    }
}
