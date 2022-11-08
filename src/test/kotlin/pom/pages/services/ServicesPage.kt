package pom.pages.services

import com.codeborne.selenide.Selenide.element
import com.github.qky666.selenidepom.pom.Required
import pom.common.MainFramePage

open class ServicesPage : MainFramePage() {
    @Required val principal = element("div.servicios-principal")
    @Required open val title = principal.find("h1.h2")
}

val servicesPage = ServicesPage()