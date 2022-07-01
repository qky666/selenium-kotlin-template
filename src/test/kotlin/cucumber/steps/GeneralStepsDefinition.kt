package cucumber.steps

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import io.cucumber.java.es.Cuando
import io.cucumber.java.es.Dado
import io.cucumber.java.es.Entonces
import org.apache.logging.log4j.kotlin.Logging
import pom.mainFramePage
import pom.servicesPage

class GeneralStepsDefinition: Logging {
    @Dado("Se accede a la web de MTP")
    fun accessMainURl() {
        Selenide.open("")
        mainFramePage.shouldLoadRequired()
    }

    @Dado("Se aceptan la cookies")
    fun acceptCookies() {
        mainFramePage.cookiesBanner.acceptCookies()
        logger.info { "Cookies accepted" }
    }

    @Cuando("Se navega a Servicios -> Aseguramiento de la calidad")
    fun navigateToQualityAssurance() {
        mainFramePage.mainMenu.services.hover()
        mainFramePage.mainMenu.servicesPopUpQualityAssurance.click()
    }

    @Entonces("Se carga la página Aseguramiento de la calidad")
    fun qualityAssurancePageIsLoaded() {
        servicesPage.shouldLoadRequired()
    }

    @Entonces("El mensaje de aviso de las cookies no se muestra")
    fun cookiesMessageNotVisible() {
        mainFramePage.cookiesBanner.self.shouldNotBe(Condition.visible)
    }
}