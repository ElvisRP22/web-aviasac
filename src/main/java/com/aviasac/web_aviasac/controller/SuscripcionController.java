package com.aviasac.web_aviasac.controller;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.aviasac.web_aviasac.model.Suscripcion;
import com.aviasac.web_aviasac.services.SuscripcionService;

@Controller
@RequestMapping("/newsletter")
public class SuscripcionController {
    
    private final SuscripcionService suscripcionService;

    public SuscripcionController(SuscripcionService suscripcionService) {
        this.suscripcionService = suscripcionService;
    }

    /**
     * Endpoint para suscribir un nuevo correo (Abierto al público)
     */
    @PostMapping("/suscribirse")
    @ResponseBody
    public ResponseEntity<?> suscribirse(@RequestParam String correo) {
        if (suscripcionService.existsByEmail(correo)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("success", false, "message", "Este correo ya está suscrito"));
        }
        Suscripcion suscriptor = new Suscripcion();
        suscriptor.setEmail(correo);
        suscriptor.setEstado(true);
        suscriptor.setFechaSuscripcion(LocalDateTime.now());

        suscripcionService.save(suscriptor);

        return ResponseEntity.ok(Map.of("success", true, "message", "¡Gracias por suscribirte!"));
    }
    
    /**
     * Endpoint para eliminar un suscriptor por ID (Protegido por JWT)
     * @param id El ID del suscriptor a eliminar.
     */
    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarSuscriptor(@PathVariable Integer id) {
        try {
            if (!suscripcionService.existById(id)) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "Suscriptor no encontrado"));
            }
            
            suscripcionService.deleteById(id);
            
            return ResponseEntity.ok(Map.of("success", true, "message", "Suscriptor eliminado correctamente"));
        } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error al eliminar: " + e.getMessage()));
        }
    }
}