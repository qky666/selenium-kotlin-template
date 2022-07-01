package util

import com.github.automatedowl.tools.AllureEnvironmentWriter
import com.github.qky666.selenidepom.SPConfig
import com.google.common.collect.ImmutableMap
import org.apache.commons.lang3.StringUtils


object Hooks {
    fun setupBrowser(browser: String, mobile: String) {

        //Allure environment
        AllureEnvironmentWriter.allureEnvironmentWriter(
            ImmutableMap.of(
                "Browser", StringUtils.capitalize(browser),
                "Mobile", StringUtils.capitalize(mobile),
            )
        )

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
}