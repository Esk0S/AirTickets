package com.currencies.mainpackage.api.controllers.html

import com.currencies.mainpackage.api.ApiPath
import com.currencies.mainpackage.api.ApiPath.ACCOUNT
import com.currencies.mainpackage.api.ApiPath.CITIES
import com.currencies.mainpackage.api.ApiPath.LOGIN
import com.currencies.mainpackage.api.ApiPath.LOGOUT
import com.currencies.mainpackage.api.ApiPath.NAME
import com.currencies.mainpackage.api.ApiPath.PURCHASE
import com.currencies.mainpackage.api.ApiPath.SEARCH
import com.currencies.mainpackage.api.ApiPath.TICKETS
import com.currencies.mainpackage.api.HtmlPath
import com.currencies.mainpackage.core.security.UserPrincipal
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping
    fun rootPage(): String {
        return "redirect:${ApiPath.HOME}"
    }

    @GetMapping(ApiPath.HOME)
    fun homePage(
        @AuthenticationPrincipal userPrincipal: UserPrincipal?,
        model: Model
    ): String {
        model.addAttribute("searchTicketsUrl", TICKETS + SEARCH)
        model.addAttribute("searchCitiesUrl", CITIES + NAME)
        model.addAttribute("ticketPurchaseUrl", PURCHASE)
        model.addAttribute("loginUrl", LOGIN)
        model.addAttribute("logoutUrl", LOGOUT)
        model.addAttribute("accountUrl", ACCOUNT)
        model.addAttribute("isLoggedIn", userPrincipal != null)

        return HtmlPath.INDEX
    }

}