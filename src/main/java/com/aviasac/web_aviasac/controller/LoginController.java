package com.aviasac.web_aviasac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Muestra login.html
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password) {
        // Validación simple (ejemplo)
        if (email.equals("usuario@aviacion.com") && password.equals("1234")) {
            return "redirect:/base"; // aquí iría la vista de usuario normal
        }
        return "redirect:/login?error";
    }
}
