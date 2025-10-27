package com.aviasac.web_aviasac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aviasac.web_aviasac.model.Usuario;
import com.aviasac.web_aviasac.services.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if(logout != null){
            System.out.println("Logout detectado");

            model.addAttribute("logout", "Sesión cerrada correctamente.");
        }

        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @GetMapping("/registro")
    public String formRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "registro";
        }
        usuarioService.save(usuario);
        return "redirect:/auth/login";
    }
}