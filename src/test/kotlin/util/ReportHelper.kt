package util

import com.codeborne.selenide.Selenide
import io.qameta.allure.Attachment
import org.openqa.selenium.OutputType

object ReportHelper {
    @Attachment(value = "Page screenshot")
    fun attachScreenshot(): ByteArray? {
        return Selenide.screenshot(OutputType.BYTES)
    }
}