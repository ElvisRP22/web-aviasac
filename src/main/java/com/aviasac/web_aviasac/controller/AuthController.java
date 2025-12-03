package com.aviasac.web_aviasac.controller;

import com.aviasac.web_aviasac.dto.AuthRequest;
import com.aviasac.web_aviasac.dto.AuthResponse;
import com.aviasac.web_aviasac.model.Usuario;
import com.aviasac.web_aviasac.services.JwtService;
import com.aviasac.web_aviasac.services.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder; // Importante
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, 
                          JwtService jwtService, 
                          UserDetailsService userDetailsService,
                          UsuarioService usuarioService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login"; 
    }

    @PostMapping("/token")
    @ResponseBody
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuario usuario, Model model) {
        try {
            // 1. Validar si el usuario ya existe (opcional pero recomendado)
            // if (usuarioService.existeEmail(usuario.getEmail())) { ... }

            // 2. Encriptar la contraseña (¡CRUCIAL!)
            String passEncriptada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passEncriptada);

            // 3. Asignar Rol por defecto (si tu lógica lo requiere)
            // usuario.setRoles("ROLE_USER"); 
            // Ojo: Esto depende de cómo manejes tus roles en la BD (String o Tabla separada)

            // 4. Guardar en BD
            usuarioService.save(usuario);

            // 5. Redirigir al login con mensaje de éxito
            return "redirect:/auth/login?registrado=true";

        } catch (Exception e) {
            // Manejo de errores (ej. email duplicado)
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            return "auth/registro";
        }
    }
}