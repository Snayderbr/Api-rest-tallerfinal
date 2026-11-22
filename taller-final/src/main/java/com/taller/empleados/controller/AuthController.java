package com.taller.empleados.controller;

import com.taller.empleados.dto.UsuarioRequest;
import com.taller.empleados.model.Rol;
import com.taller.empleados.model.Usuario;
import com.taller.empleados.repository.RolRepository;
import com.taller.empleados.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarios;
    private final RolRepository roles;
    private final PasswordEncoder encoder;

    public AuthController(UsuarioRepository usuarios, RolRepository roles, PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.roles = roles;
        this.encoder = encoder;
    }

    // Registro público: siempre crea un "usuario normal" (ignora cualquier rol enviado)
    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> registro(@Valid @RequestBody UsuarioRequest req) {
        if (usuarios.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }
        Rol rolUser = roles.findByNombre("ROLE_USER").orElseThrow();
        Usuario u = new Usuario();
        u.setUsername(req.username());
        u.setPassword(encoder.encode(req.password()));
        u.setRoles(Set.of(rolUser));
        usuarios.save(u);
        return Map.of("mensaje", "Usuario registrado con rol USER");
    }
}
