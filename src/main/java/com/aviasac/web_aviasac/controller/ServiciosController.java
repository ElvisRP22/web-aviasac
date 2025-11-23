package com.aviasac.web_aviasac.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.aviasac.web_aviasac.model.Servicio;
import com.aviasac.web_aviasac.services.ServicioService;

@Controller
@RequestMapping("/servicios")
public class ServiciosController {

    private final ServicioService servicioService;

    public ServiciosController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping("")
    public String servicios(Model model) {
        model.addAttribute("servicios", servicioService.findAll());
        return "servicios";
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarServicio(
            @ModelAttribute Servicio servicio,
            @RequestParam("archivoImagen") MultipartFile archivoImagen) {

        try {
            String carpeta = "uploads/";
            Path ruta = Paths.get(carpeta);

            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta);
            }

            if (!archivoImagen.isEmpty()) {
                String nombreArchivo = Paths.get(archivoImagen.getOriginalFilename())
                        .getFileName().toString();

                Path destino = ruta.resolve(nombreArchivo);
                Files.copy(archivoImagen.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                servicio.setUrlImagen(nombreArchivo);
            }

            servicioService.save(servicio);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "Servicio registrado correctamente"
                    ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al guardar el servicio: " + e.getMessage()
                    ));
        }
    }

}
