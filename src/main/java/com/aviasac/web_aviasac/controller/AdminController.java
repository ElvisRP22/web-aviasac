package com.aviasac.web_aviasac.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        // Datos simulados
        model.addAttribute("usuariosTotales", 1247);
        model.addAttribute("ventasMes", "$48,960");
        model.addAttribute("pedidosPendientes", 23);
        model.addAttribute("productosActivos", 342);

        // Último acceso dinámico
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        model.addAttribute("ultimoAcceso", LocalDateTime.now().format(formatter));

        // Lista simulada de mensajes de usuarios
        List<Map<String, String>> mensajes = new ArrayList<>();
        
        Map<String, String> m1 = new HashMap<>();
        m1.put("nombre", "Juan Pérez");
        m1.put("email", "juan@example.com");
        m1.put("contenido", "Necesito información sobre fumigación en campos de arroz.");
        m1.put("fecha", "Hace 5 minutos");
        mensajes.add(m1);

        Map<String, String> m2 = new HashMap<>();
        m2.put("nombre", "María González");
        m2.put("email", "maria@example.com");
        m2.put("contenido", "¿Pueden atender zonas rurales en Piura?");
        m2.put("fecha", "Hace 20 minutos");
        mensajes.add(m2);

        Map<String, String> m3 = new HashMap<>();
        m3.put("nombre", "Carlos López");
        m3.put("email", "carlos@example.com");
        m3.put("contenido", "Solicito cotización de servicio aéreo.");
        m3.put("fecha", "Hace 1 hora");
        mensajes.add(m3);

        model.addAttribute("mensajes", mensajes);

        return "admin"; // templates/admin.html
    }
}

