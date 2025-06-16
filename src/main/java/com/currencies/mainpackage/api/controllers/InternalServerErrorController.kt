package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.ApiPath.ERROR
import com.currencies.mainpackage.core.exception.InternalServerErrorException
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@CrossOrigin
@RequestMapping(ERROR)
class InternalServerErrorController: ErrorController {

    @GetMapping
    fun error() {
        throw InternalServerErrorException()
    }

}