package com.currencies.mainpackage.api.controllers.html

import com.currencies.mainpackage.api.ApiPath
import com.currencies.mainpackage.api.ApiPath.HOME
import com.currencies.mainpackage.api.ApiPath.LOGOUT
import com.currencies.mainpackage.api.ApiPath.PROFILE
import com.currencies.mainpackage.api.ApiPath.TICKET_DETAILS
import com.currencies.mainpackage.api.ApiPath.USERS
import com.currencies.mainpackage.api.HtmlPath
import com.currencies.mainpackage.core.security.UserPrincipal
import com.currencies.mainpackage.core.ticketpurchase.TicketPurchaseService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AccountHtmlController(private val ticketPurchaseService: TicketPurchaseService) {

    @GetMapping(ApiPath.ACCOUNT)
    fun profile(
        model: Model,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): String {
        val purchases = ticketPurchaseService.getAllByUserId(userPrincipal.id)
        model.addAttribute("user", userPrincipal.user)
        model.addAttribute("purchases", purchases)
        model.addAttribute("isPurchaseButtonShown", false)
        model.addAttribute("ticketDetailsUrl", ApiPath.ACCOUNT + TICKET_DETAILS)
        model.addAttribute("updateUserUrl", USERS + PROFILE)
        model.addAttribute("homeUrl", HOME)
        model.addAttribute("logoutUrl", LOGOUT)

        return HtmlPath.ACCOUNT
    }

}