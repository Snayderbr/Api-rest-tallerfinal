package com.taller.empleados.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

// roles es opcional: ["ADMIN"], ["USER"] o ambos
public record UsuarioRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres") String password,
        Set<String> roles) {
}
