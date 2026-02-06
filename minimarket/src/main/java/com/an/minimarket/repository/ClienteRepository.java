package com.an.minimarket.repository;

import com.an.minimarket.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente,Integer> {
    //Buscar Cliente por Nombre
    List<Cliente>findByNombreContainingIgnoreCase(String nombre);
    //Buscar Cliente por Telefono
    List<Cliente>findByTelefonoContainingIgnoreCase(String telefono);
    //Validar que el telefono sea unico
    boolean existsByTelefonoIgnoreCaseAndIdClienteNot(String telefono , Integer idCliente);
    boolean existsByTelefonoIgnoreCase(String telefono);
}
