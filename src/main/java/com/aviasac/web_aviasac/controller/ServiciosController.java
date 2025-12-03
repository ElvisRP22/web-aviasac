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
import com.aviasac.web_aviasac.services.TipoDeCultivoService;
import com.aviasac.web_aviasac.services.TrabajoService;

@Controller
@RequestMapping("/servicios")
public class ServiciosController {

    private final ServicioService servicioService;
    private final TipoDeCultivoService tipoDeCultivoService;
    private final TrabajoService trabajoService;

    public ServiciosController(ServicioService servicioService, 
        TipoDeCultivoService tipoDeCultivoService,
        TrabajoService trabajoService) {
        this.servicioService = servicioService;
        this.tipoDeCultivoService = tipoDeCultivoService;
        this.trabajoService=trabajoService;
    }

    @GetMapping("")
    public String servicios(Model model) {
        model.addAttribute("servicios", servicioService.findAll());
        model.addAttribute("tiposDeCultivo", tipoDeCultivoService.findAll());
        model.addAttribute("trabajos", trabajoService.findAll());
        return "servicios";
    }

    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarServicio(
            @ModelAttribute Servicio servicio,
            @RequestParam(value = "archivoImagen", required = false) MultipartFile archivoImagen) {

        try {
            Servicio servicioBD = null;

            if (servicio.getId() != null) {
                servicioBD = servicioService.findById(servicio.getId()).orElse(null);

                if (servicioBD != null) {
                    servicio.setUrlImagen(servicioBD.getUrlImagen());
                }
            }

            String carpeta = "uploads/";
            Path ruta = Paths.get(carpeta);

            if (!Files.exists(ruta)) {
                Files.createDirectories(ruta);
            }

            if (archivoImagen != null && !archivoImagen.isEmpty()) {
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
                            "message", "Servicio guardado correctamente"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "success", false,
                            "message", "Error al guardar el servicio: " + e.getMessage()));
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarServicio(@PathVariable Integer id) {
        try {
            servicioService.deleteById(id);

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "message", "Servicio eliminado correctamente"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Map.of(
                                    "success", false,
                                    "message", "Error al eliminar el servicio: " + e.getMessage()));
        }
    }

}
