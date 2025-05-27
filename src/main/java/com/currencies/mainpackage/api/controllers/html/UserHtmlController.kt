package com.currencies.mainpackage.api.controllers.html

import com.currencies.mainpackage.api.ApiPath
import com.currencies.mainpackage.api.ApiPath.SIGN_UP
import com.currencies.mainpackage.api.HtmlPath
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserHtmlController {

    @GetMapping(SIGN_UP)
    fun signUpPage(model: Model): String {
        model.addAttribute("signUpUrl", ApiPath.USERS + ApiPath.SIGN_UP)
        return HtmlPath.REGISTER
    }

}