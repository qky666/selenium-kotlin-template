package testng

import com.codeborne.selenide.CollectionCondition.size
import com.codeborne.selenide.Condition.disappear
import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide
import com.github.qky666.selenidepom.config.SPConfig
import com.github.qky666.selenidepom.data.TestData
import com.github.qky666.selenidepom.pom.shouldLoadRequired
import org.apache.logging.log4j.kotlin.Logging
import org.testng.Assert
import org.testng.ITestResult
import org.testng.annotations.*
import pom.pages.home.homePage
import pom.pages.services.servicesPage
import pom.searchresults.searchResultsPage
import util.ReportHelper

open class MtpKotlinTest : Logging {

    private var data = TestData("prod")

    private val maxResultsPerPageExpected = 5

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

    @Test(
        description = "User navigate to Quality Assurance (desktop)", groups = ["desktop"]
    )
    fun userNavigateToQualityAssuranceDesktop() {
        homePage.shouldLoadRequired().acceptCookies()
        homePage.shouldLoadRequired().mainBanner.verifyTextsEs()
        homePage.mainMenu.services.hover()
        homePage.mainMenu.servicesPopUp.qualityAssurance.click()
        servicesPage.shouldLoadRequired()
    }

    @Test(
        description = "User navigate to Quality Assurance (mobile)", groups = ["mobile"]
    )
    fun userNavigateToQualityAssuranceMobile() {
        homePage.shouldLoadRequired().acceptCookies()
        homePage.shouldLoadRequired().mainBanner.verifyTextsEs()
        homePage.mobileMenuButton.click()
        val mobileMenu = homePage.mobileMenu
        mobileMenu.shouldLoadRequired().shouldBeCollapsed()
        mobileMenu.services.click()
        mobileMenu.servicesQualityAssurance.shouldBe(visible).click()
        servicesPage.shouldLoadRequired()
    }

    @Test(
        description = "Forced failure", groups = ["desktop", "mobile"]
    )
    fun forcedFailure() {
        homePage.shouldLoadRequired().acceptCookies()
        homePage.shouldLoadRequired().mainBanner.verifyTextsEs()
        servicesPage.shouldLoadRequired()
    }

    @Test(
        description = "Cookies should not reappear after accepted (desktop)", groups = ["desktop"]
    )
    fun userNavigateToQualityAssuranceCookiesShouldNotReappearDesktop() {
        homePage.shouldLoadRequired().acceptCookies()
        homePage.shouldLoadRequired().mainBanner.verifyTextsEs()
        homePage.mainMenu.services.hover()
        homePage.mainMenu.servicesPopUp.qualityAssurance.click()
        servicesPage.shouldLoadRequired().cookiesBanner.shouldNotBe(visible)
    }

    @Test(
        description = "Cookies should not reappear after accepted (mobile)", groups = ["mobile"]
    )
    fun userNavigateToQualityAssuranceCookiesShouldNotReappearMobile() {
        homePage.shouldLoadRequired().acceptCookies()
        homePage.shouldLoadRequired().mainBanner.verifyTextsEs()
        homePage.mobileMenuButton.click()
        val mobileMenu = homePage.mobileMenu
        mobileMenu.shouldLoadRequired().shouldBeCollapsed()
        mobileMenu.services.click()
        mobileMenu.servicesQualityAssurance.shouldBe(visible).click()
        servicesPage.shouldLoadRequired().cookiesBanner.shouldNotBe(visible)
    }

    @Test(
        description = "Search (desktop)", groups = ["desktop"], dataProvider = "searchData"
    )
    fun search(search: Search) {
        homePage.shouldLoadRequired().acceptCookies()
        homePage.shouldLoadRequired().mainBanner.verifyTextsEs()
        homePage.mainMenu.searchOpen.click()
        homePage.mainMenu.searchMenu.shouldLoadRequired().searchInput.sendKeys(search.search)
        homePage.mainMenu.searchMenu.doSearch.click()
        homePage.mainMenu.searchMenu.should(disappear)

        searchResultsPage.shouldLoadRequired().breadcrumb.activeBreadcrumbItem.shouldHave(exactText("Results: ${search.search}"))
        searchResultsPage.breadcrumb.breadcrumbItems[0].shouldHave(exactText("Home"))
        Assert.assertEquals(searchResultsPage.searchResults.shouldLoadRequired().count(), maxResultsPerPageExpected)
        searchResultsPage.pagination.shouldLoadRequired().currentPage.shouldHave(exactText("1"))
        searchResultsPage.pagination.nextPage.shouldBe(visible)
        searchResultsPage.pagination.pagesLinks.shouldHave(size(search.resultsPagesExpected))[search.resultsPagesExpected - 2].shouldHave(
            exactText(search.resultsPagesExpected.toString())
        )
        searchResultsPage.pagination.pagesLinks.find(exactText(search.resultsPagesExpected.toString())).click()

        searchResultsPage.shouldLoadRequired().pagination.shouldLoadRequired().currentPage.shouldHave(
            exactText(
                search.resultsPagesExpected.toString()
            )
        )
        searchResultsPage.pagination.nextPage.should(disappear)
        searchResultsPage.pagination.previousPage.shouldBe(visible)
        Assert.assertEquals(
            searchResultsPage.searchResults.shouldLoadRequired().count(), search.lastPageResultsExpected
        )
        val result = searchResultsPage.searchResults.filterBy(text(search.lastPageResultTitle)).shouldHave(size(1))[0]
        result.title.shouldHave(exactText(search.lastPageResultTitle))
        result.text.shouldHave(text(search.lastPageResultText))
    }

    @DataProvider(parallel = true)
    fun searchData(): Array<Search> {
        return arrayOf(
            Search(
                "Mexico",
                2,
                3,
                "MTP, 25 años como empresa de referencia en aseguramiento de negocios digitales",
                "MTP es hoy una empresa de referencia en Digital Business Assurance"
            ), Search(
                "Viajero",
                2,
                2,
                "Los valores MTP, claves para este 2020",
                "Este año 2020 ha sido un año particular y totalmente atípico para todos"
            )
        )
    }

    data class Search(
        val search: String,
        val resultsPagesExpected: Int,
        val lastPageResultsExpected: Int,
        val lastPageResultTitle: String,
        val lastPageResultText: String
    )
}
