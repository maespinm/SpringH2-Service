package com.example.microsvc_h2.controller;

import com.example.microsvc_h2.model.Producto;
import com.example.microsvc_h2.repository.RepositorioProducto;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/productos")
public class ControladorProducto {

    private final RepositorioProducto repositorioProducto;

    public ControladorProducto(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @GetMapping
    public List<Producto> listarProductos() {
        return repositorioProducto.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return repositorioProducto.findById(id)
                .map(producto -> ResponseEntity.ok().body(producto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return repositorioProducto.save(producto);
    }

    @PostMapping("/varios")
    public List<Producto> crearProductos(@RequestBody List<Producto> productos) {
        return repositorioProducto.saveAll(productos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarProducto(@PathVariable Long id) {
        if (repositorioProducto.existsById(id)) {
            repositorioProducto.deleteById(id);
            String mensaje = "{\"mensaje\":\"Producto " + id + " eliminado con éxito!\"}";
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json")
                    .body(mensaje);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return repositorioProducto.findById(id)
                .map(producto -> {
                    if (productoActualizado.getNombre() != null && !productoActualizado.getNombre().isEmpty()) {
                        producto.setNombre(productoActualizado.getNombre());
                    }
                    if (productoActualizado.getPrecio() != null) {
                        producto.setPrecio(productoActualizado.getPrecio());
                    }
                    Producto productoGuardado = repositorioProducto.save(producto);
                    return ResponseEntity.ok(productoGuardado);
                })
                .orElse(ResponseEntity.notFound().build());
    }



}