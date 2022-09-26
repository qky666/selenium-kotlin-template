package pom

import com.codeborne.selenide.Selenide.element
import com.github.qky666.selenidepom.annotation.Required
import com.github.qky666.selenidepom.pom.Page

open class MainFramePage : Page() {
    @Required("desktop") val mainMenu = MainMenuWidget(element("div.custom-menu"))
    @Required("mobile") val mobileMenuButton = element("button.custom-menu-btn-flotante")
    val mobileMenu = MobileMenuWidget(element("div#menu-movil ul.uk-nav"))
    val cookiesBanner = CookiesBannerWidget(element("div#cookie-law-info-bar"))
}

val mainFramePage = MainFramePage()
