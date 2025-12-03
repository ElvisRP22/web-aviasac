package com.aviasac.web_aviasac.controller;

import com.aviasac.web_aviasac.model.*;
import com.aviasac.web_aviasac.services.ServicioService;
import com.aviasac.web_aviasac.services.SolicitudService;
import com.aviasac.web_aviasac.services.TipoDeCultivoService;
import com.aviasac.web_aviasac.services.UsuarioService;
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
    private SolicitudService solicitudService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private TipoDeCultivoService tipoDeCultivoService;

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarSolicitud(
            @Valid @ModelAttribute Solicitud solicitud,
            BindingResult bindingResult,
            @RequestParam(value = "servicioId", required = false) Integer servicioId,
            @RequestParam(value = "tipoDeCultivoId", required = false) Integer tipoDeCultivoId,
            Principal principal) {

        Map<String, Object> response = new HashMap<>();

        if (servicioId == null) {
            bindingResult.rejectValue("servicio", "required", "El servicio es obligatorio");
        }

        if (tipoDeCultivoId == null) {
            bindingResult.rejectValue("tipoDeCultivo", "required", "El tipo de cultivo es obligatorio");
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                String campo = error.getField();
                String mensaje = error.getDefaultMessage();
                errores.put(campo, mensaje);
            }
            response.put("status", "error");
            response.put("errors", errores);
            response.put("message", "Hay errores de validación en el formulario");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            if (principal == null) {
                response.put("status", "error");
                response.put("message", "La sesión ha expirado. Por favor inicie sesión nuevamente.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            String username = principal.getName();
            Usuario usuario = usuarioService.buscarPorUsername(username);

            if (usuario == null) {
                response.put("status", "error");
                response.put("message", "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Servicio servicio = servicioService.findById(servicioId)
                    .orElseThrow(() -> new RuntimeException("El servicio seleccionado no existe"));

            TipoDeCultivo tipo = tipoDeCultivoService.findById(tipoDeCultivoId)
                    .orElseThrow(() -> new RuntimeException("El tipo de cultivo seleccionado no existe"));

            solicitud.setUsuario(usuario);
            solicitud.setServicio(servicio);
            solicitud.setTipoDeCultivo(tipo);

            solicitudService.save(solicitud);

            response.put("status", "success");
            response.put("message",
                    "¡Solicitud enviada con éxito! Nos comunicaremos al número: " + usuario.getTelefono());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "Error interno al procesar la solicitud: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/actualizar-estado/{id}")
    @ResponseBody
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam("estado") String estado) {

        try {
            Solicitud solicitud = solicitudService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

            SolicitudEstado nuevoEstado = SolicitudEstado.valueOf(estado);
            solicitud.setEstado(nuevoEstado);
            solicitudService.save(solicitud);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Estado actualizado correctamente a: " + nuevoEstado.name()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "success", false,
                            "message", "Estado no válido"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al actualizar el estado: " + e.getMessage()));
        }
    }

    @GetMapping("/detalle/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerDetalle(@PathVariable Integer id) {
        try {
            Solicitud solicitud = solicitudService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

            Map<String, Object> detalle = new HashMap<>();
            detalle.put("id", solicitud.getId());
            detalle.put("cliente", solicitud.getUsuario().getNombre());
            detalle.put("email", solicitud.getUsuario().getEmail());
            detalle.put("telefono", solicitud.getUsuario().getTelefono());
            detalle.put("servicio", solicitud.getServicio().getNombre());
            detalle.put("cultivo", solicitud.getTipoDeCultivo().getNombre());
            detalle.put("numHectareas", solicitud.getNumHectareas());
            detalle.put("ubicacion", solicitud.getUbicacion());
            detalle.put("areaParaAterrizar", solicitud.getAreaParaAterrizar());
            detalle.put("mensaje", solicitud.getMensaje());
            detalle.put("fechaPreferida",
                    solicitud.getFechaPreferida() != null ? solicitud.getFechaPreferida().toString() : null);
            detalle.put("estado", solicitud.getEstado().name());
            detalle.put("fechaSolicitud", solicitud.getFechaSolicitud().toString());

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "solicitud", detalle));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "success", false,
                            "message", "Solicitud no encontrada: " + e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarSolicitud(@PathVariable Integer id) {
        try {
            solicitudService.deleteById(id);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Solicitud eliminada correctamente"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al eliminar la solicitud: " + e.getMessage()));
        }
    }
}