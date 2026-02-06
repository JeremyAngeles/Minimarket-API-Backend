package com.an.minimarket.controller;

import com.an.minimarket.model.Cliente;
import com.an.minimarket.model.Venta;
import com.an.minimarket.service.ClienteService;
import com.an.minimarket.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cliente-app")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    VentaService ventaService;

    //Agregar Clientes
    @PostMapping("/clientes")
    public ResponseEntity<Cliente>agregarCliente(@Valid @RequestBody Cliente cliente){
        Cliente nuevoCliente = clienteService.agregarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    //Listar Clientes
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>>listarClientes(){
        return ResponseEntity.ok( clienteService.listarClientes());
    }

    //Buscar Por Id
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente>buscarClientePorId(@PathVariable Integer id){
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    //Modificar Cliente
    @PutMapping("/clientes/{id}")
    public ResponseEntity<Cliente>modificarClientePorId(@PathVariable Integer id,
                                                        @Valid @RequestBody Cliente clienteRecibido){
        return ResponseEntity.ok(clienteService.modificarCliente(id , clienteRecibido));
    }

    //Eliminar Cliente
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id){
        this.clienteService.eliminarClientePorId(id);
        return ResponseEntity.noContent().build();
    }

    //Listar Ventas de un Cliente
    @GetMapping("/clientes/{id}/ventas")
    public ResponseEntity<List<Venta>> listarVentasClientes(@PathVariable Integer id){
        return ResponseEntity.ok(ventaService.listarVentasDeUnCliente(id));
    }

    //Buscar Cliente por Nombre
    @GetMapping("/clientes/buscar-por-nombre")
    public ResponseEntity<List<Cliente>>buscarClientePorNombre(@RequestParam String nombre){
        return ResponseEntity.ok(clienteService.buscarNombreCliente(nombre));
    }

    //Buscar Cliente por Telefono
    @GetMapping("/clientes/buscar-por-telefono")
    public ResponseEntity<List<Cliente>>buscarClientePorTelefono(@RequestParam String telefono){
        return ResponseEntity.ok(clienteService.buscarPorTelefono(telefono));
    }
}
