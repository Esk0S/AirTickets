package com.currencies.mainpackage.api.controllers.html

import com.currencies.mainpackage.api.ApiPath
import com.currencies.mainpackage.api.HtmlPath
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
    fun homePage(model: Model): String {
        model.addAttribute("searchUrl", "/tickets/search")
        return HtmlPath.INDEX
    }

}