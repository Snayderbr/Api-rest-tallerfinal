package com.taller.empleados.config;

import com.taller.empleados.model.Rol;
import com.taller.empleados.model.Usuario;
import com.taller.empleados.repository.RolRepository;
import com.taller.empleados.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;


@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RolRepository roles, UsuarioRepository usuarios, PasswordEncoder encoder) {
        return args -> {
            Rol admin = roles.findByNombre("ROLE_ADMIN").orElseGet(() -> roles.save(new Rol("ROLE_ADMIN")));
            Rol user = roles.findByNombre("ROLE_USER").orElseGet(() -> roles.save(new Rol("ROLE_USER")));

            if (!usuarios.existsByUsername("admin")) {
                Usuario a = new Usuario();
                a.setUsername("admin");
                a.setPassword(encoder.encode("admin123"));
                a.setRoles(Set.of(admin));
                usuarios.save(a);
            }
            if (!usuarios.existsByUsername("usuario")) {
                Usuario u = new Usuario();
                u.setUsername("usuario");
                u.setPassword(encoder.encode("usuario123"));
                u.setRoles(Set.of(user));
                usuarios.save(u);
            }
        };
    }
}
