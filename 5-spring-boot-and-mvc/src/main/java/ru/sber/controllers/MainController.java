package ru.sber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/home_page")
    public String homePage() {
        return "pages/main.html";
    }
}
