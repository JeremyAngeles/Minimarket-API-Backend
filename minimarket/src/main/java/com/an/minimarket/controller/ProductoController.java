package com.an.minimarket.controller;

import com.an.minimarket.model.Producto;
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
@RequestMapping("/producto-app")
@CrossOrigin(origins = "*")
public class ProductoController {
    @Autowired
    ProductoService productoService;


    //Agregar Productos
    @PostMapping("/productos")
    public ResponseEntity<Producto>agregarProducto(@Valid @RequestBody Producto producto){
        Producto nuevoProducto = productoService.agregarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    //Listar Productos
    @GetMapping("/productos")
    public ResponseEntity<List<Producto>>listarProductos(){
        return ResponseEntity.ok(this.productoService.listarProductos());
    }

    //Buscar Producto por Id
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto>buscarProductoPorId(@PathVariable Integer id){
        return ResponseEntity.ok(this.productoService.buscarProductoPorId(id));
    }

    //Modificar Producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto>modificarProducto(@PathVariable Integer id ,
                                                     @Valid @RequestBody Producto productoRecibido){
        return ResponseEntity.ok(this.productoService.modificarProducto(id , productoRecibido));
    }

    //Eliminar Producto
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void>eliminarProducto(@PathVariable Integer id){
        this.productoService.eliminarProductoPorId(id);
        return ResponseEntity.noContent().build();
    }

    //Buscar por Nombre
    @GetMapping("/productos/buscar-por-nombre")
    public ResponseEntity<List<Producto>>listarProductosPorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(productoService.buscarProductoPorNombre(nombre));
    }

    //Buscar por Precio Minimo y Maximo
    @GetMapping("/productos/buscar-precio")
    public ResponseEntity<List<Producto>>listarProductosPorPrecioMinyMax(@RequestParam Double precioMin,
                                                                         @RequestParam Double precioMax){
        return ResponseEntity.ok(this.productoService.buscarPrecioMinMax(precioMin,precioMax));
    }

    //Buscar por Stock Bajo
    @GetMapping("/productos/buscar-stock-bajo")
    public ResponseEntity<List<Producto>>buscarProductoStockBajo( @RequestParam Integer stock){
        return ResponseEntity.ok(this.productoService.buscarStockBajo(stock));
    }

}
