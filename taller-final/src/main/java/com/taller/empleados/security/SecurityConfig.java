package com.taller.empleados.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // API REST stateless (Postman), sin formularios
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Registro público (crea usuario normal)
                .requestMatchers("/api/auth/**").permitAll()
                // Cualquier rol autenticado puede consultar
                .requestMatchers(HttpMethod.GET, "/api/empleados/**").hasAnyRole("ADMIN", "USER")
                // Usuario normal y admin pueden agregar y editar
                .requestMatchers(HttpMethod.POST, "/api/empleados/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/api/empleados/**").hasAnyRole("ADMIN", "USER")
                // Solo admin puede eliminar
                .requestMatchers(HttpMethod.DELETE, "/api/empleados/**").hasRole("ADMIN")
                // Gestión de usuarios solo admin
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
