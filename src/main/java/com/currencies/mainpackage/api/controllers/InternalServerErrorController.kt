package com.currencies.mainpackage.api.controllers

import com.currencies.mainpackage.api.ApiPath.ERROR
import com.currencies.mainpackage.core.exception.InternalServerErrorException
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping(ERROR)
class InternalServerErrorController: ErrorController {

    @GetMapping
    fun error(): ResponseEntity<Unit> {
        throw InternalServerErrorException()
    }

}