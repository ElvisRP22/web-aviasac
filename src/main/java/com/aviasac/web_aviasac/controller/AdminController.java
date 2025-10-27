package com.aviasac.web_aviasac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    // Ruta principal del panel de administración
    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        // Aquí puedes enviar datos dinámicos a la vista si deseas
        model.addAttribute("titulo", "Panel de Administración | Aviación Agrícola SAC");
        return "admin"; // corresponde al archivo admin.html en templates
    }

    // Sección de cotizaciones
    @GetMapping("/admin/cotizaciones")
    public String cotizaciones(Model model) {
        model.addAttribute("titulo", "Gestión de Cotizaciones");
        return "admin"; // reutiliza el mismo template (admin.html)
    }

    // Sección de newsletter
    @GetMapping("/admin/newsletter")
    public String newsletter(Model model) {
        model.addAttribute("titulo", "Suscriptores del Newsletter");
        return "admin";
    }

    // Sección de contactos
    @GetMapping("/admin/contactos")
    public String contactos(Model model) {
        model.addAttribute("titulo", "Consultas de Contacto");
        return "admin";
    }

    // Sección de reportes
    @GetMapping("/admin/reportes")
    public String reportes(Model model) {
        model.addAttribute("titulo", "Reportes y Estadísticas");
        return "admin";
    }

}
