package testng

import com.codeborne.selenide.Condition
import org.testng.annotations.Test
import pom.mainFramePage
import pom.servicesPage

class MtpKotlinMobile: MtpKotlinBase() {
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