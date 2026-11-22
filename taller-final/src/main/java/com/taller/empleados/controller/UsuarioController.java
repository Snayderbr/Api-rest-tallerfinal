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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Solo admin: crear usuarios eligiendo su rol
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarios;
    private final RolRepository roles;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarios, RolRepository roles, PasswordEncoder encoder) {
        this.usuarios = usuarios;
        this.roles = roles;
        this.encoder = encoder;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarios.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario crear(@Valid @RequestBody UsuarioRequest req) {
        if (usuarios.existsByUsername(req.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }
        Set<Rol> asignados = new HashSet<>();
        Set<String> pedidos = (req.roles() == null || req.roles().isEmpty()) ? Set.of("USER") : req.roles();
        for (String r : pedidos) {
            String nombre = "ROLE_" + r.toUpperCase().replace("ROLE_", "");
            asignados.add(roles.findByNombre(nombre)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol inválido: " + r)));
        }
        Usuario u = new Usuario();
        u.setUsername(req.username());
        u.setPassword(encoder.encode(req.password()));
        u.setRoles(asignados);
        return usuarios.save(u);
    }
}
