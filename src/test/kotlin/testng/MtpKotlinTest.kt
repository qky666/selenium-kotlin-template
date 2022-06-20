package testng

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import org.apache.logging.log4j.kotlin.Logging
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import pom.mainFramePage
import pom.servicesPage

open class MtpKotlinTest: Logging {

    @BeforeMethod
    fun openBrowser() {
        Selenide.open("")
        logger.info {"URL opened"}
    }

    @AfterMethod
    fun closeBrowser() {
        Selenide.closeWebDriver()
        logger.info {"Closed webdriver"}
    }

    private fun acceptCookies() {
        mainFramePage.shouldLoadRequired()
        mainFramePage.cookiesBanner.acceptCookies()
        logger.info {"Cookies accepted"}
    }

    @Test
    fun userNavigateToQualityAssuranceDesktop() {
        acceptCookies()
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        servicesPage.shouldLoadRequired()
    }

    @Test
    fun userNavigateToQualityAssuranceCookiesShouldNotReappearDesktop() {
        acceptCookies()
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
        servicesPage.shouldLoadRequired()
        mainFramePage.cookiesBanner.self.shouldNotBe(Condition.visible)
    }

    @Test
    fun userNavigateToQualityAssuranceMobile() {
        acceptCookies()
        mainFramePage.mobileMenuButton.click()
        mainFramePage.mobileMenu.shouldLoadRequired()
        mainFramePage.mobileMenu.shouldBeCollapsed()
        mainFramePage.mobileMenu.services.click()
        mainFramePage.mobileMenu.servicesQualityAssurance.shouldBe(Condition.visible).click()
        servicesPage.shouldLoadRequired()
    }

    @Test
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
