package com.aviasac.web_aviasac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginAdminController {

    @GetMapping("/loginAdmin")
    public String loginAdmin() {
        return "loginAdmin"; // Muestra administrador.html
    }

    @PostMapping("/loginAdmin")
    public String administradorLogin(@RequestParam String email,
            @RequestParam String password) {
        // Validación simple (ejemplo)
        if (email.equals("admin@aviacion.com") && password.equals("admin123")) {
            return "redirect:/admin/dashboard"; // aquí iría el panel de admin
        }
        return "redirect:/loginAdmin?error";
    }
}
