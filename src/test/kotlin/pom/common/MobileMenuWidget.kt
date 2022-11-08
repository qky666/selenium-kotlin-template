package pom.common

import com.codeborne.selenide.CollectionCondition
import com.codeborne.selenide.Condition.exactText
import com.codeborne.selenide.Condition.text
import com.codeborne.selenide.SelenideElement
import com.github.qky666.selenidepom.config.SPConfig
import com.github.qky666.selenidepom.pom.ConditionedElement
import com.github.qky666.selenidepom.pom.Required
import com.github.qky666.selenidepom.pom.Widget
import org.openqa.selenium.WebElement

class MobileMenuWidget(self: SelenideElement) : Widget(self) {
    @Required val mobileMenuButton = find("button.custom-menu-btn-flotante")
    @Required val langEn = findAll("li.individual-menu-idioma>a").findBy(text("en"))
    @Required val langEs = findAll("li.individual-menu-idioma>a").findBy(text("es"))
    @Required val selectedLang = ConditionedElement(
        find("li.individual-menu-idioma.idioma-activo>a"), mapOf("es" to exactText("es"), "en" to exactText("en"))
    )
}

class MobileMenuPopUpWidget(self: SelenideElement) : Widget(self) {
    // First level menu items

    // All first level menu items
    @Suppress("MemberVisibilityCanBePrivate") val firstLevelMenuItems = findAll("li.uk-parent")

    @Required
    @JvmOverloads
    fun services(lang: String = SPConfig.lang): SelenideElement {
        return if (lang == "en") {
            firstLevelMenuItems.findBy(exactText("Services"))
        } else firstLevelMenuItems.findBy(exactText("Servicios"))
    }

    @Required
    @JvmOverloads
    fun areas(lang: String = SPConfig.lang): SelenideElement {
        return if (lang == "en") {
            firstLevelMenuItems.findBy(exactText("Areas"))
        } else firstLevelMenuItems.findBy(exactText("Sectores"))
    }

    // Possible bug in mtp.es in english, it only appears on desktop view
    @Required(lang = "es")
    @JvmOverloads
    fun training(lang: String = SPConfig.lang): SelenideElement {
        return if (lang == "en") {
            firstLevelMenuItems.findBy(exactText("Training"))
        } else firstLevelMenuItems.findBy(exactText("Formación"))
    }

    @Required(lang = "es") val blog = firstLevelMenuItems.findBy(exactText("Blog"))

    @Required
    @JvmOverloads
    fun talent(lang: String = SPConfig.lang): SelenideElement {
        return if (lang == "en") {
            firstLevelMenuItems.findBy(exactText("Talent"))
        } else firstLevelMenuItems.findBy(exactText("Talento"))
    }

    @Required
    @JvmOverloads
    fun about(lang: String = SPConfig.lang): SelenideElement {
        return if (lang == "en") {
            firstLevelMenuItems.findBy(exactText("About MTP"))
        } else firstLevelMenuItems.findBy(exactText("Sobre MTP"))
    }

    @Required
    @JvmOverloads
    fun contact(lang: String = SPConfig.lang): SelenideElement {
        return if (lang == "en") {
            findAll("li>a").findBy(exactText("Contact Us"))
        } else findAll("li>a").findBy(exactText("Contacto"))
    }

    // Second level menú items. I only write one, but there are more
    fun servicesQualityAssurance(lang: String = SPConfig.lang): SelenideElement {
        return if (lang == "en") {
            findAll("a").findBy(exactText("Quality Assurance"))
        } else findAll("a").findBy(exactText("Aseguramiento de la calidad"))
    }

    fun shouldBeCollapsed() {
        firstLevelMenuItems.shouldHave(
            CollectionCondition.allMatch("All firstLevelMenuItems have aria-expanded=false") { element: WebElement ->
                "false".equals(element.getAttribute("aria-expanded"), ignoreCase = true)
            }
        )
    }
}