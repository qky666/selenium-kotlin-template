package pom.menu.desktop

import com.codeborne.selenide.SelenideElement
import com.github.qky666.selenidepom.pom.Required
import com.github.qky666.selenidepom.pom.Widget

class ServicesPopupMenuWidget(self: SelenideElement) : Widget(self) {
    @Required val qualityAssurance = find("a[data-principal='Aseguramiento de la calidad']")
}