package cucumber

import com.codeborne.selenide.Selenide
import io.cucumber.java.After
import io.cucumber.java.Scenario
import org.openqa.selenium.OutputType

class Hooks {
    @After
    fun addScreenshotIfFailed(scenario: Scenario) {
        if (scenario.isFailed) {
            scenario.attach(Selenide.screenshot(OutputType.BYTES), "image/png", scenario.name)
        }
    }
}