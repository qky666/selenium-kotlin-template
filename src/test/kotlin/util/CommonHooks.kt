package util

import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.SPConfig
import org.apache.logging.log4j.kotlin.Logging
import org.testng.ITestResult


object CommonHooks: Logging {
    fun setupBrowser(browser: String, mobile: String) {
        // Configure WebDriver
        if (mobile.equals("true", true)) {
            SPConfig.resetSelenideConfig()
            SPConfig.addMobileEmulation()
            SPConfig.setPomVersion("mobile")
        } else {
            SPConfig.getSelenideConfig().browser(browser)
            SPConfig.setPomVersion("desktop")
        }
        SPConfig.setWebDriver()
    }

    fun testNGAttachScreenshotIfFailedAndCloseWebDriver(result: ITestResult) {
        if (result.status != ITestResult.SUCCESS) {
            ReportHelper.attachScreenshot("Test failed screenshot")
        }
        Selenide.closeWebDriver()
        logger.info { "Closed webdriver for test ${result.name}. Status: ${result.status}" }
    }
}