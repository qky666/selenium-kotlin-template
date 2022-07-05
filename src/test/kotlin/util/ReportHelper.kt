package util

import com.codeborne.selenide.Selenide
import io.qameta.allure.Allure
import org.openqa.selenium.OutputType

object ReportHelper {
    fun attachScreenshot(name: String = "Page screenshot") {
        val screenshot = Selenide.screenshot(OutputType.BYTES)
        if (screenshot != null) {
            Allure.addAttachment(name, screenshot.inputStream())
        } else {
            Allure.addAttachment(name, "No screenshot available")
        }
    }
}