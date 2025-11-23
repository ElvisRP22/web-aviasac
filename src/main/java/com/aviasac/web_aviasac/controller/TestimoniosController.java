package com.aviasac.web_aviasac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.aviasac.web_aviasac.services.PreguntaFrecuenteService;

@Controller
public class TestimoniosController {

    private final PreguntaFrecuenteService pFrecuenteService;

    public TestimoniosController(PreguntaFrecuenteService pFrecuenteService) {
        this.pFrecuenteService = pFrecuenteService;
    }

    @GetMapping("/testimonios")
    public String testimonios(Model model) {
        model.addAttribute("preguntasFrecuentes", pFrecuenteService.findOnlyActive());
        return "testimonios";
    }

}
