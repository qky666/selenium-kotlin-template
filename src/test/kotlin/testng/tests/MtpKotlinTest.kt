package testng.tests

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
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
import util.Hooks.setupBrowser
import util.ReportHelper

open class MtpKotlinTest : Logging {

    @BeforeMethod(description = "Open base URL in browser", alwaysRun = true)
    @Parameters("browser", "mobile")
    fun beforeMethod(browser: String, mobile: String) {
        setupBrowser(browser, mobile)

        // Open URL
        Selenide.open("")
        logger.info { "URL opened. Browser: $browser. Mobile: $mobile" }
    }

    @AfterMethod(description = "Close browser", alwaysRun = true)
    fun afterMethod(result: ITestResult) {
        if (result.status != ITestResult.SUCCESS) {
            ReportHelper.attachScreenshot()
        }
        Selenide.closeWebDriver()
        logger.info { "Closed webdriver for test ${result.name}. Status: ${result.status}" }
    }

    @Step("Accept cookies")
    private fun acceptCookies() {
        mainFramePage.shouldLoadRequired()
        mainFramePage.cookiesBanner.acceptCookies()
        logger.info { "Cookies accepted" }
    }

    @Test(description = "Desktop. User navigate to Quality Assurance", retryAnalyzer = Retry::class)
    fun userNavigateToQualityAssuranceDesktop() {
        acceptCookies()
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        servicesPage.shouldLoadRequired()
    }

    @Test(description = "Desktop. Forced failure", retryAnalyzer = Retry::class)
    fun forcedFailureDesktop() {
        acceptCookies()
        servicesPage.shouldLoadRequired()
    }

    @Test(description = "Desktop. Cookies should not reappear after accepted", retryAnalyzer = Retry::class)
    fun userNavigateToQualityAssuranceCookiesShouldNotReappearDesktop() {
        acceptCookies()
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        servicesPage.shouldLoadRequired()
        mainFramePage.cookiesBanner.self.shouldNotBe(Condition.visible)
    }

    @Test(description = "Mobile. User navigate to Quality Assurance", groups = ["Mobile"], retryAnalyzer = Retry::class)
    fun userNavigateToQualityAssuranceMobile() {
        acceptCookies()
        mainFramePage.mobileMenuButton.click()
        mainFramePage.mobileMenu.shouldLoadRequired()
        mainFramePage.mobileMenu.shouldBeCollapsed()
        mainFramePage.mobileMenu.services.click()
        mainFramePage.mobileMenu.servicesQualityAssurance.shouldBe(Condition.visible).click()
        servicesPage.shouldLoadRequired()
    }

    @Test(description = "Mobile. Forced failure", groups = ["Mobile"], retryAnalyzer = Retry::class)
    fun forcedFailureMobile() {
        acceptCookies()
        servicesPage.shouldLoadRequired()
    }

    @Test(
        description = "Mobile. Cookies should not reappear after accepted",
        groups = ["Mobile"],
        retryAnalyzer = Retry::class
    )
    fun userNavigateToQualityAssuranceCookiesShouldNotReappearMobile() {
        acceptCookies()
        mainFramePage.mobileMenuButton.click()
        mainFramePage.mobileMenu.shouldLoadRequired()
        mainFramePage.mobileMenu.shouldBeCollapsed()
        mainFramePage.mobileMenu.services.click()
        mainFramePage.mobileMenu.servicesQualityAssurance.shouldBe(Condition.visible).click()
        servicesPage.shouldLoadRequired()
        mainFramePage.cookiesBanner.self.shouldNotBe(Condition.visible)
    }
}
