package com.aviasac.web_aviasac.controller;

import com.aviasac.web_aviasac.model.Usuario;
import com.aviasac.web_aviasac.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String verPerfil(Model model, Principal principal) {
        if (principal == null) return "redirect:/auth/login";

        String username = principal.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        model.addAttribute("usuario", usuario);
        
        return "perfil";
    }

    @PostMapping("/actualizar")
    public String actualizarPerfil(@ModelAttribute Usuario usuarioForm, 
                                   Principal principal,
                                   RedirectAttributes redirectAttributes) {
        try {
            String username = principal.getName();
            Usuario usuarioActual = usuarioService.buscarPorUsername(username);
            usuarioActual.setNombre(usuarioForm.getNombre());
            usuarioActual.setTelefono(usuarioForm.getTelefono());
            usuarioService.save(usuarioActual);
            
            redirectAttributes.addFlashAttribute("mensaje", "Perfil actualizado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al actualizar perfil");
        }
        return "redirect:/perfil";
    }
}