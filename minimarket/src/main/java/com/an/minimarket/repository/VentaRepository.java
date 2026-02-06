package com.an.minimarket.repository;

import com.an.minimarket.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta,Integer>{
    //Buscar Ventas de un Cliente
    List<Venta>findByClienteIdCliente(Integer idCliente);
    //Buscar Ventas por Estado
    List<Venta>findByEstadoContainingIgnoreCase(String estado);
    //Buscar Ventas por Fecha
    List<Venta>findByFechaBetween(LocalDateTime inicio , LocalDateTime fin);
    //Validar si existe el id cliente
    boolean existsByClienteIdCliente(int idCliente);
}
