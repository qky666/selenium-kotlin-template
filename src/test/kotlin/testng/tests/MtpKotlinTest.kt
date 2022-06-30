package testng.tests

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.SPConfig
import io.qameta.allure.Step
import org.apache.logging.log4j.kotlin.Logging
import org.testng.ITestResult
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Parameters
import org.testng.annotations.Test
import pom.mainFramePage
import pom.servicesPage
import util.ReportHelper

open class MtpKotlinTest : Logging {

    @BeforeMethod(description = "Open base URL in browser", alwaysRun = true)
    @Parameters("browser", "mobile")
    fun setupAndOpenBrowser(browser: String, mobile: String) {
        if (mobile.equals("true", true)) {
            SPConfig.resetSelenideConfig()
            SPConfig.addMobileEmulation()
            SPConfig.setPomVersion("mobile")
        } else {
            SPConfig.getSelenideConfig().browser(browser)
            SPConfig.setPomVersion("desktop")
        }
        SPConfig.setWebDriver()
        Selenide.open("")
        logger.info { "URL opened" }
    }

    @AfterMethod(description = "Close browser", alwaysRun = true)
    fun closeBrowser(result: ITestResult) {
        if (result.status == ITestResult.FAILURE) {
            ReportHelper.attachScreenshot()
        }
        Selenide.closeWebDriver()
        logger.info { "Closed webdriver" }
    }

    @Step("Accept cookies")
    private fun acceptCookies() {
        mainFramePage.shouldLoadRequired()
        mainFramePage.cookiesBanner.acceptCookies()
        logger.info { "Cookies accepted" }
    }

    @Test(description = "Desktop. User navigate to Quality Assurance")
    fun userNavigateToQualityAssuranceDesktop() {
        acceptCookies()
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        servicesPage.shouldLoadRequired()
    }

    @Test(description = "Desktop. Forced failure")
    fun forcedFailureDesktop() {
        acceptCookies()
        servicesPage.shouldLoadRequired()
    }

    @Test(description = "Desktop. Cookies should not reappear after accepted")
    fun userNavigateToQualityAssuranceCookiesShouldNotReappearDesktop() {
        acceptCookies()
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        servicesPage.shouldLoadRequired()
        mainFramePage.cookiesBanner.self.shouldNotBe(Condition.visible)
    }

    @Test(description = "Mobile. User navigate to Quality Assurance", groups = ["Mobile"])
    fun userNavigateToQualityAssuranceMobile() {
        acceptCookies()
        mainFramePage.mobileMenuButton.click()
        mainFramePage.mobileMenu.shouldLoadRequired()
        mainFramePage.mobileMenu.shouldBeCollapsed()
        mainFramePage.mobileMenu.services.click()
        mainFramePage.mobileMenu.servicesQualityAssurance.shouldBe(Condition.visible).click()
        servicesPage.shouldLoadRequired()
    }

    @Test(description = "Mobile. Forced failure", groups = ["Mobile"])
    fun forcedFailureMobile() {
        acceptCookies()
        servicesPage.shouldLoadRequired()
    }

    @Test(description = "Mobile. Cookies should not reappear after accepted", groups = ["Mobile"])
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
