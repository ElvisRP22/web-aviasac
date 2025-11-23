package com.aviasac.web_aviasac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private CustomLoginSuccessHandler customLoginSuccessHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                                .permitAll()
                                                .requestMatchers("/img/**", "/uploads/**").permitAll()
                                                .requestMatchers(
                                                                "/auth/registro",
                                                                "/auth/login",
                                                                "/",
                                                                "/servicios",
                                                                "/servicios/**",
                                                                "/beneficios",
                                                                "/beneficios/**",
                                                                "/testimonios",
                                                                "/nosotros",
                                                                "/contactanos",
                                                                "/contacto/enviar",
                                                                "/newsletter/suscribirse")
                                                .permitAll()

                                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                                                .requestMatchers("/servicios/guardar").hasAuthority("ROLE_ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/auth/login")
                                                .successHandler(customLoginSuccessHandler)
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/auth/login?logout")
                                                .permitAll());

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}