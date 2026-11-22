package com.taller.empleados.controller;

import com.taller.empleados.model.Empleado;
import com.taller.empleados.repository.EmpleadoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoRepository repo;

    public EmpleadoController(EmpleadoRepository repo) {
        this.repo = repo;
    }

    // READ (cualquier rol autenticado)
    @GetMapping
    public List<Empleado> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Empleado obtener(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado"));
    }

    // CREATE (ADMIN y USER)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empleado crear(@Valid @RequestBody Empleado e) {
        e.setId(null);
        return repo.save(e);
    }

    // UPDATE (ADMIN y USER)
    @PutMapping("/{id}")
    public Empleado actualizar(@PathVariable Long id, @Valid @RequestBody Empleado datos) {
        Empleado e = obtener(id);
        e.setNombre(datos.getNombre());
        e.setApellido(datos.getApellido());
        e.setEmail(datos.getEmail());
        e.setCargo(datos.getCargo());
        e.setSalario(datos.getSalario());
        return repo.save(e);
    }

    // DELETE (solo ADMIN)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        obtener(id);
        repo.deleteById(id);
    }
}
