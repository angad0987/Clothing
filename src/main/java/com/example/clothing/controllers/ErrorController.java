package com.example.clothing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/errorPage")
    public String errorPage() {
        return "errorPage";
    }

}
