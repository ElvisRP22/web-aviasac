package com.aviasac.web_aviasac.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aviasac.web_aviasac.model.Suscripcion;
import com.aviasac.web_aviasac.services.SuscripcionService;

@Controller
@RequestMapping("/newsletter")
public class SuscripcionController {
    
    private final SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }


    @PostMapping("/suscribirse")
    @ResponseBody
    public ResponseEntity<?> suscribirse(@RequestParam String correo) {
        // Verificar si el correo ya está suscrito
        if (suscripcionService.existsByEmail(correo)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "Este correo ya está suscrito"));
        }
        // Crear nuevo suscriptor
        Suscripcion suscriptor = new Suscripcion();
        suscriptor.setEmail(correo);
        suscriptor.setEstado(true);
        suscriptor.setFechaSuscripcion(LocalDateTime.now());

        suscripcionService.save(suscriptor);

        return ResponseEntity.ok(Map.of("success", true, "message", "¡Gracias por suscribirte!"));
    }
}
