package com.currencies.mainpackage.api.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
@RequestMapping
public class Controller {

    @GetMapping
    public String toStartPage(Model model) {
        return "index";
    }

}