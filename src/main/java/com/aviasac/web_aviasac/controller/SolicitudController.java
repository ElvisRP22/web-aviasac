package com.aviasac.web_aviasac.controller;

import com.aviasac.web_aviasac.model.*;
import com.aviasac.web_aviasac.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudRepository solicitudRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ServicioRepository servicioRepository;
    @Autowired
    private TipoDeCultivoRepository tipoDeCultivoRepository;

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarSolicitud(
            @Valid @ModelAttribute Solicitud solicitud,
            BindingResult bindingResult,
            @RequestParam("servicioId") Integer servicioId,
            @RequestParam("tipoDeCultivoId") Integer tipoDeCultivoId,
            Principal principal) {

        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errores.put(error.getField(), error.getDefaultMessage());
            }
            response.put("status", "error");
            response.put("errors", errores);
            return ResponseEntity.badRequest().body(response);
        }

        try {
            if (principal == null) {
                response.put("status", "error");
                response.put("message", "La sesión ha expirado. Por favor inicie sesión nuevamente.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String username = principal.getName();
            Usuario usuario = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));

            Servicio servicio = servicioRepository.findById(servicioId)
                    .orElseThrow(() -> new RuntimeException("El servicio seleccionado no existe"));

            TipoDeCultivo tipo = tipoDeCultivoRepository.findById(tipoDeCultivoId)
                    .orElseThrow(() -> new RuntimeException("El tipo de cultivo seleccionado no existe"));

            solicitud.setUsuario(usuario);
            solicitud.setServicio(servicio);
            solicitud.setTipoDeCultivo(tipo);
            solicitudRepository.save(solicitud);
            response.put("status", "success");
            response.put("message", "¡Solicitud enviada con éxito! Nos comunicaremos al número: " + usuario.getTelefono());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error interno al procesar la solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}