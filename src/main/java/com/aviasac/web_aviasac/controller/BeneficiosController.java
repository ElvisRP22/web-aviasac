package com.aviasac.web_aviasac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeneficiosController {
    @GetMapping("/beneficios")
    public String beneficios(){
        return "beneficios";
    }
}
