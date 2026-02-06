package com.an.minimarket.controller;

import com.an.minimarket.model.DetalleVenta;
import com.an.minimarket.service.DetalleVentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/detalleVenta-app")
@CrossOrigin(origins = "*")
public class DetalleVentaController {

    @Autowired
    DetalleVentaService detalleVentaService;

    //Agregar Detalle Venta
    @PostMapping("/detalle-ventas")
    public ResponseEntity<DetalleVenta>agregarDetalleVenta(@Valid @RequestBody DetalleVenta detalleVenta){
        DetalleVenta nuevoDetalleVenta = detalleVentaService.agregarDetalleVenta(detalleVenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalleVenta);
    }

    //Listar Detalle Venta
    @GetMapping("/detalle-ventas")
    public ResponseEntity<List<DetalleVenta>>listarDetallaVenta(){
        return ResponseEntity.ok(this.detalleVentaService.listarDetalleVentas());
    }

    //buscar Detalle Venta por Id
    @GetMapping("/detalle-ventas/{id}")
    public ResponseEntity<DetalleVenta>buscarDetalleVentaPorId(@PathVariable Integer id){
        return ResponseEntity.ok(this.detalleVentaService.buscarDetalleVentaPorId(id));
    }

    //Eliminar Detalle Venta
    @DeleteMapping("/detalle-ventas/{id}")
    public ResponseEntity<Void>eliminarDetalleVenta(@PathVariable Integer id){
        this.detalleVentaService.eliminarDetalleVentaPorId(id);
        return ResponseEntity.noContent().build();
    }

    //Listar Detalles por ID Venta
    @GetMapping("/ventas/{idVenta}/detalles")
    public ResponseEntity<List<DetalleVenta>>listarDetalles(@PathVariable Integer idVenta){
        return ResponseEntity.ok(detalleVentaService.listarDetallesDeVenta(idVenta));
    }
}
