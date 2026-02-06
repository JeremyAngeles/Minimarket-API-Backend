package com.an.minimarket.controller;

import com.an.minimarket.model.Categoria;
import com.an.minimarket.model.Producto;
import com.an.minimarket.service.CategoriaService;
import com.an.minimarket.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categoria-app")
@CrossOrigin(origins = "*")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @Autowired
    ProductoService productoService;

    //Crear Categoria
    @PostMapping("/categorias")
    public ResponseEntity<Categoria>agregarCategoria(@Valid @RequestBody Categoria categoria){
        Categoria nuevaCategoria = categoriaService.agregarCategoria(categoria);
        return  ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    //Listar Categorias
    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>>listarCategorias(){
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    //Buscar Categoria por Id
    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria>buscarCategoriPorId(@PathVariable Integer id){
        return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
    }

    //Modificar Categoria por Id
    @PutMapping("/categorias/{id}")
    public ResponseEntity<Categoria>modificarCategoriaPorId(@PathVariable Integer id,
                                                          @Valid @RequestBody Categoria categoriaRecibida){
        return ResponseEntity.ok(categoriaService.modificarCategoria(id , categoriaRecibida));
    }

    //Eliminar Categoria por Id
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void>eliminarCategoriaPorId(@PathVariable Integer id){
        categoriaService.eliminarCategoriaPorId(id);
        return ResponseEntity.noContent().build();
    }

    //Listar Productos Por Categoria
    @GetMapping("/categorias/{id}/productos")
    public ResponseEntity<List<Producto>>listarProductosPorCategoria(@PathVariable Integer id){
        return ResponseEntity.ok(productoService.listarProductosPorCategoria(id));
    }

    //Buscar Nombre de Categoria
    @GetMapping("/categorias/buscar-nombre-categoria")
    public ResponseEntity<List<Categoria>>buscarCategoriaPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(categoriaService.buscarCategoriaPorNombre(nombre));
    }
}
