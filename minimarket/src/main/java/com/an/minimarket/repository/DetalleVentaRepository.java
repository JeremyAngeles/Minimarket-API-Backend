package com.an.minimarket.repository;

import com.an.minimarket.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta,Integer> {
    //Buscar Detalles de Ventas
    List<DetalleVenta>findByVentaIdVenta(Integer idVenta);
    //Validar si el producto tiene ventas asociadas
    boolean existsByProductoIdProducto(Integer idProducto);
    //Validacion para proteger el borrado de Ventas
    boolean existsByVentaIdVenta(Integer idVenta);
}
