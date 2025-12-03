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

import com.aviasac.web_aviasac.model.Trabajo;
import com.aviasac.web_aviasac.services.TrabajoService;

@Controller
@RequestMapping("/trabajos")
public class TrabajoController {
    
    private final TrabajoService trabajoService;

    public TrabajoController(TrabajoService trabajoService) {
        this.trabajoService = trabajoService;
    }

    @GetMapping("")
    public String trabajos(Model model) {
        model.addAttribute("trabajos", trabajoService.findAll());
        return "trabajos";
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarTrabajo(
            @ModelAttribute Trabajo trabajo,
            @RequestParam(value = "archivoImagen", required = false) MultipartFile archivoImagen) {

        try {
            Trabajo trabajoBD = null;

            // Si tiene ID → es edición → recuperar datos actuales
            if (trabajo.getId() != null) {
                trabajoBD = trabajoService.findById(trabajo.getId()).orElse(null);

                if (trabajoBD != null) {
                    // Mantener el archivo actual si NO se selecciona uno nuevo
                    trabajo.setUrlFile(trabajoBD.getUrlFile());
                }
            }

            String carpeta = "uploads/trabajos/";
            Path ruta = Paths.get(carpeta);

            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta);
            }

            // Si se sube archivo → reemplazar el anterior
            if (archivoImagen != null && !archivoImagen.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + 
                        Paths.get(archivoImagen.getOriginalFilename())
                        .getFileName().toString();

                Path destino = ruta.resolve(nombreArchivo);
                Files.copy(archivoImagen.getInputStream(), destino, 
                          StandardCopyOption.REPLACE_EXISTING);

                trabajo.setUrlFile(nombreArchivo);
            }

            trabajoService.save(trabajo);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "success", true,
                            "message", "Trabajo guardado correctamente"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al guardar el trabajo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarTrabajo(@PathVariable Integer id) {
        try {
            // Opcional: eliminar el archivo del servidor
            trabajoService.findById(id).ifPresent(trabajo -> {
                if (trabajo.getUrlFile() != null && !trabajo.getUrlFile().isEmpty()) {
                    try {
                        Path archivoPath = Paths.get("uploads/trabajos/" + trabajo.getUrlFile());
                        Files.deleteIfExists(archivoPath);
                    } catch (Exception e) {
                        System.err.println("No se pudo eliminar el archivo: " + e.getMessage());
                    }
                }
            });

            trabajoService.deleteById(id);

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Trabajo eliminado correctamente"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al eliminar el trabajo: " + e.getMessage()));
        }
    }

    @GetMapping("/obtener/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerTrabajo(@PathVariable Integer id) {
        try {
            Trabajo trabajo = trabajoService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trabajo no encontrado"));

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "trabajo", trabajo));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "success", false,
                            "message", "Trabajo no encontrado: " + e.getMessage()));
        }
    }
}