package com.aviasac.web_aviasac.config;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.aviasac.web_aviasac.model.Rol;
import com.aviasac.web_aviasac.model.Usuario;
import com.aviasac.web_aviasac.repository.RolRepository;
import com.aviasac.web_aviasac.repository.UsuarioRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UsuarioRepository usuarioRepository, RolRepository rolRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (rolRepository.count() > 0 || usuarioRepository.count() > 0) {
            System.out.println("La base de datos ya contiene datos de seguridad, no se cargaran datos adicionales.");
            return;
        }

        System.out.println("Cargando datos de seguridad (roles y usuarios)...");

        Rol rolAdmin = new Rol();
        rolAdmin.setNombre("ROLE_ADMIN");

        Rol rolUser = new Rol();
        rolUser.setNombre("ROLE_USER");

        rolRepository.saveAll(List.of(rolAdmin, rolUser));

        Usuario admin = new Usuario();
        admin.setNombre("Elvis");
        admin.setUsername("elvisdev");
        admin.setEmail("eremaycunarp544@gmail.com");
        admin.setPassword(passwordEncoder.encode("1234"));
        admin.setTelefono("988000923");
        admin.setRol(rolAdmin);

        Usuario cliente = new Usuario();
        cliente.setNombre("Luis");
        cliente.setUsername("luis22");
        cliente.setEmail("luis100@gmail.com");
        cliente.setPassword(passwordEncoder.encode("123"));
        cliente.setTelefono("988110923");
        cliente.setRol(rolUser);

        usuarioRepository.saveAll(List.of(admin, cliente));

        System.out.println("Datos de seguridad cargados exitosamente.");

    }

}