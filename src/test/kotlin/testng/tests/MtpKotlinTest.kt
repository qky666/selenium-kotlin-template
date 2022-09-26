package testng.tests

import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.config.SPConfig
import com.github.qky666.selenidepom.data.TestData
import com.github.qky666.selenidepom.pom.shouldLoadRequired
import io.qameta.allure.Step
import org.apache.logging.log4j.kotlin.Logging
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Parameters
import org.testng.annotations.Test
import pom.mainFramePage
import pom.servicesPage
import testng.retry.Retry
import util.ReportHelper

open class MtpKotlinTest : Logging {

    private var data = TestData("prod")

    @BeforeMethod(description = "Open base URL in browser", alwaysRun = true)
    @Parameters("browser", "mobile", "env")
    fun beforeMethod(browser: String, mobile: String, env: String) {
        // Set env
        data.resetData(env)
        val baseUrl = data.input.getProperty("data.input.baseUrl")
        // Configure webdriver
        if (mobile.equals("true", true)) {
            SPConfig.setupBasicMobileBrowser()
        } else {
            SPConfig.setupBasicDesktopBrowser(browser)
        }
        // Open URL
        Selenide.open(baseUrl)
        logger.info { "URL opened. Browser: $browser. Mobile: $mobile" }
    }

    @AfterMethod(description = "Close browser", alwaysRun = true)
    fun afterMethod(result: ITestResult) {
        if (result.status != ITestResult.SUCCESS) {
            ReportHelper.attachScreenshot("Test failed screenshot")
        }
        Selenide.closeWebDriver()
        logger.info { "Closed webdriver for test ${result.name}. Status: ${result.status}" }
    }

    @Step("Accept cookies")
    private fun acceptCookies() {
        mainFramePage.shouldLoadRequired().cookiesBanner.acceptCookies()
        logger.info { "Cookies accepted" }
    }

    @Test(
        description = "User navigate to Quality Assurance (desktop)",
        groups = ["desktop"],
        retryAnalyzer = Retry::class
    )
    fun userNavigateToQualityAssuranceDesktop() {
        acceptCookies()
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        servicesPage.shouldLoadRequired()
    }

    @Test(
        description = "User navigate to Quality Assurance (mobile)",
        groups = ["mobile"],
        retryAnalyzer = Retry::class
    )
    fun userNavigateToQualityAssuranceMobile() {
        acceptCookies()
        mainFramePage.mobileMenuButton.click()
        val mobileMenu = mainFramePage.mobileMenu
        mobileMenu.shouldLoadRequired().shouldBeCollapsed()
        mobileMenu.services.click()
        mobileMenu.servicesQualityAssurance.shouldBe(visible).click()
        servicesPage.shouldLoadRequired()
    }

    @Test(description = "Forced failure", groups = ["desktop", "mobile"], retryAnalyzer = Retry::class)
    fun forcedFailure() {
        acceptCookies()
        servicesPage.shouldLoadRequired()
    }

    @Test(
        description = "Cookies should not reappear after accepted (desktop)",
        groups = ["desktop"],
        retryAnalyzer = Retry::class
    )
    fun userNavigateToQualityAssuranceCookiesShouldNotReappearDesktop() {
        acceptCookies()
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        servicesPage.shouldLoadRequired().cookiesBanner.shouldNotBe(visible)
    }

    @Test(
        description = "Cookies should not reappear after accepted (mobile)",
        groups = ["mobile"],
        retryAnalyzer = Retry::class
    )
    fun userNavigateToQualityAssuranceCookiesShouldNotReappearMobile() {
        acceptCookies()
        mainFramePage.mobileMenuButton.click()
        val mobileMenu = mainFramePage.mobileMenu
        mobileMenu.shouldLoadRequired().shouldBeCollapsed()
        mobileMenu.services.click()
        mobileMenu.servicesQualityAssurance.shouldBe(visible).click()
        servicesPage.shouldLoadRequired().cookiesBanner.shouldNotBe(visible)
    }
}
