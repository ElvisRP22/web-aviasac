package com.aviasac.web_aviasac.controller;

import com.aviasac.web_aviasac.model.Testimonio;
import com.aviasac.web_aviasac.model.Usuario;
import com.aviasac.web_aviasac.services.PreguntaFrecuenteService;
import com.aviasac.web_aviasac.services.TestimonioService;
import com.aviasac.web_aviasac.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

@Controller
public class TestimoniosController {

    @Autowired
    private TestimonioService testimonioService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PreguntaFrecuenteService faqService;

    @GetMapping("/testimonios")
    public String testimonios(Model model) {
        model.addAttribute("testimonios", testimonioService.findAll());
        model.addAttribute("preguntasFrecuentes", faqService.findOnlyActive()); 
        return "testimonios";
    }

    @PostMapping("/testimonios/guardar")
    public String guardarTestimonio(
            @RequestParam("comentario") String descripcion,
            @RequestParam("calificacion") Integer calificacion,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        try {
            if (principal == null) {
                return "redirect:/auth/login";
            }
            String username = principal.getName();
            Usuario usuario = usuarioService.buscarPorUsername(username);
            if (testimonioService.existeTestimonioDeUsuario(usuario)) {
                redirectAttributes.addFlashAttribute("error", "Ya has publicado un testimonio anteriormente.");
                return "redirect:/testimonios";
            }
            Testimonio testimonio = new Testimonio();
            testimonio.setUsuario(usuario);
            testimonio.setComentario(descripcion);
            testimonio.setCalificacion(calificacion);
            testimonioService.save(testimonio);
            
            redirectAttributes.addFlashAttribute("mensaje", "¡Gracias! Tu testimonio ha sido publicado.");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Ocurrió un error al guardar tu opinión.");
        }

        return "redirect:/testimonios";
    }
}