package com.aviasac.web_aviasac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aviasac.web_aviasac.model.Testimonio;

@Controller
public class TestimoniosController {
    
    Testimonio testimonio = new Testimonio(1, "Juan Perez", "Excelente servicio, muy recomendado.");

    @GetMapping("/testimonios")
    public String testimonios(Model model) {
        model.addAttribute("testimonio", testimonio);
        return "testimonios";
    }
}
