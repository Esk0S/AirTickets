package com.currencies.mainpackage.api.controllers.html

import com.currencies.mainpackage.api.ApiPath
import com.currencies.mainpackage.api.HtmlPath
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping
class AuthHtmlController {

    @GetMapping(ApiPath.LOGIN)
    fun loginPage(model: Model, @RequestParam(required = false) error: String?): String {
        model.addAttribute("loginUrl", ApiPath.LOGIN)
        model.addAttribute("registerUrl", ApiPath.SIGN_UP)
        if (error != null) {
            model.addAttribute("errorType", "loginError")
            model.addAttribute(
                "customErrors",
                mapOf("loginError" to "Неверное имя пользователя или пароль")
            )
        }
        return HtmlPath.LOGIN
    }

}