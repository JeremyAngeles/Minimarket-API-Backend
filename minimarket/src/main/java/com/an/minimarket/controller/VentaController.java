    package com.an.minimarket.controller;
    import org.springframework.format.annotation.DateTimeFormat;
    import com.an.minimarket.model.Venta;
    import com.an.minimarket.service.VentaService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.time.LocalDateTime;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/venta-app")
    @CrossOrigin(origins = "*")
    public class VentaController {

        @Autowired
        VentaService ventaService;

        //Agregar Ventas
        @PostMapping("/ventas/cliente/{idCliente}")
        public ResponseEntity<Venta>crearVenta(@PathVariable Integer idCliente){
            Venta nuevaVenta = ventaService.agregarVenta(idCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
        }

        //Listar Ventas
        @GetMapping("/ventas")
        public ResponseEntity<List<Venta>>listarVentas(){
            return ResponseEntity.ok(this.ventaService.listarVentas());
        }

        //Buscar Venta por Id
        @GetMapping("/ventas/{id}")
        public ResponseEntity<Venta>buscarVentaPorId(@PathVariable Integer id){
            return ResponseEntity.ok(this.ventaService.buscarVentaPorId(id));
        }

        //Modificar por Id
        @PutMapping("/ventas/{id}")
        public ResponseEntity<Venta>modificarVentaPorId(@PathVariable Integer id,
                                                        @Valid @RequestBody Venta ventaRecibida){
            return ResponseEntity.ok(this.ventaService.modificarVenta(id , ventaRecibida));
        }

        //Cerrar Venta
        @PutMapping("/ventas/{id}/cerrar")
        public ResponseEntity<Venta>cerrarVenta(@PathVariable Integer id){
            return  ResponseEntity.ok(this.ventaService.cerrarVenta(id));
        }

        //Eliminar Venta
        @DeleteMapping("/ventas/{id}")
        public ResponseEntity<Void>eliminarVenta(@PathVariable Integer id){
            this.ventaService.eliminarVentaPorId(id);
            return ResponseEntity.noContent().build();
        }

        //Buscar ventas por Estado
        @GetMapping("/ventas/buscar-por-estado")
        public ResponseEntity<List<Venta>>listarVentasPorEstado(@RequestParam String estado){
            return ResponseEntity.ok(this.ventaService.listarVentasPorEstado(estado));
        }

        //Buscar ventas por Fecha
        @GetMapping("/ventas/buscar-fechas")
        public ResponseEntity<List<Venta>>listarVentaPorFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin){
            return ResponseEntity.ok(this.ventaService.listarVentaPorFecha(inicio,fin));
        }
    }
