package com.an.minimarket.service.Interfaz;

import com.an.minimarket.model.DetalleVenta;

import java.util.List;

public interface IDetalleVentaService {
    List<DetalleVenta> listarDetalleVentas();

    DetalleVenta buscarDetalleVentaPorId(Integer idDetalleVenta);

    DetalleVenta agregarDetalleVenta(DetalleVenta detalleVenta);

    void eliminarDetalleVentaPorId(Integer idDetalleVenta);

    //Listar detalles de venta
    List<DetalleVenta>listarDetallesDeVenta(Integer idVenta);
}
