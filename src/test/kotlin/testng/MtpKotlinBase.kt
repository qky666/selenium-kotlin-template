package testng

import com.codeborne.selenide.Selenide
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import pom.mainFramePage

open class MtpKotlinBase {

    @BeforeMethod
    fun openBrowser() {
        Selenide.open("")
    }

    @AfterMethod
    fun closeBrowser() {
        Selenide.closeWebDriver()
    }

    protected fun acceptCookies() {
        mainFramePage.shouldLoadRequired()
        mainFramePage.cookiesBanner.acceptCookies()
    }
}
