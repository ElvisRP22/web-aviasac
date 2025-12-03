package com.aviasac.web_aviasac.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aviasac.web_aviasac.services.PreguntaFrecuenteService;
import com.aviasac.web_aviasac.services.ServicioService;
import com.aviasac.web_aviasac.services.SolicitudService;
import com.aviasac.web_aviasac.services.SuscripcionService;
import com.aviasac.web_aviasac.services.TestimonioService;
import com.aviasac.web_aviasac.services.TrabajoService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ServicioService servicioService;
    @Autowired
    private SuscripcionService suscripcionService;
    @Autowired
    private PreguntaFrecuenteService pFrecuenteService;
    @Autowired
    private SolicitudService solicitudService;
    @Autowired
    private TestimonioService testimonioService;
    @Autowired
    private TrabajoService trabajoService;

    @GetMapping
    public String index() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("serviciosTotales", servicioService.serviciosTotales());
        model.addAttribute("solicitudesTotales", solicitudService.solicitudesTotales());
        model.addAttribute("suscripcionesTotales", suscripcionService.suscriptoresTotales());
        model.addAttribute("testimoniosTotales", testimonioService.testimoniosTotales());
        return "admin/dashboard";
    }

    @GetMapping("/servicios")
    public String servicios(Model model) {
        model.addAttribute("activePage", "servicios");
        model.addAttribute("servicios", servicioService.findAll());
        return "admin/servicios/index";
    }

    @GetMapping("/cotizaciones")
    public String cotizaciones(Model model) {
        model.addAttribute("activePage", "cotizaciones");
        model.addAttribute("solicitudes", solicitudService.findAll());
        return "admin/cotizaciones/index";
    }

    @GetMapping("/faqs")
    public String faqs(Model model) {
        model.addAttribute("activePage", "faqs");
        model.addAttribute("faqs", pFrecuenteService.findAll());
        return "admin/faqs/index";
    }

    @GetMapping("/newsletter")
    public String newsletter(Model model) {
        model.addAttribute("activePage", "newsletter");
        model.addAttribute("suscriptores", suscripcionService.findAll());

        return "admin/newsletter/index";
    }

    @GetMapping("/testimonios")
    public String testimonios(Model model){
        model.addAttribute("activePage", "testimonios");
        model.addAttribute("testimonios", testimonioService.findAll());

        return "admin/testimonios/index";
    }

    @GetMapping("/trabajos")
    public String trabajos(Model model){
        model.addAttribute("activePage", "trabajos");
        model.addAttribute("trabajos", trabajoService.findAll());

        return "admin/trabajos/index";
    }
}