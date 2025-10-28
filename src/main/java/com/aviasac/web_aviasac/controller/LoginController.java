package com.aviasac.web_aviasac.controller;

import org.springframework.stereotype.Controller;

@Controller
public class LoginController {

    //@GetMapping("/login")
    public String login() {
        return "login"; 
    }

}
