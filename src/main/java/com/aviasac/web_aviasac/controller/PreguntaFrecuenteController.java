package com.aviasac.web_aviasac.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.aviasac.web_aviasac.model.PreguntaFrecuente;
import com.aviasac.web_aviasac.services.PreguntaFrecuenteService;

@Controller
@RequestMapping("/faqs")
public class PreguntaFrecuenteController {
    private final PreguntaFrecuenteService pFrecuenteService;

    public PreguntaFrecuenteController(PreguntaFrecuenteService pFrecuenteService) {
        this.pFrecuenteService = pFrecuenteService;
    }

    @PostMapping("/crear")
    @ResponseBody
    public ResponseEntity<?> crearFaq(@ModelAttribute PreguntaFrecuente faq) {
        pFrecuenteService.save(faq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("success", true, "message", "FAQ creada correctamente"));
    }

    @PostMapping("/editar/{id}")
    @ResponseBody
    public ResponseEntity<?> editarFaq(@PathVariable Integer id, @ModelAttribute PreguntaFrecuente faq) {
        faq.setId(id);
        pFrecuenteService.save(faq);
        return ResponseEntity.ok(Map.of("success", true, "message", "FAQ actualizada correctamente"));
    }

    @PostMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarFaq(@PathVariable Integer id) {
        pFrecuenteService.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "FAQ eliminada correctamente"));
    }
}