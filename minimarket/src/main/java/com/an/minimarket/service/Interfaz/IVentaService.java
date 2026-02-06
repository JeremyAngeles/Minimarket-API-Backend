package com.an.minimarket.service.Interfaz;


import com.an.minimarket.model.Venta;

import java.time.LocalDateTime;
import java.util.List;

public interface IVentaService {

    List<Venta> listarVentas();

    Venta buscarVentaPorId(Integer idVenta);

    Venta modificarVenta(Integer id , Venta ventaRecibido);

    Venta agregarVenta(Integer idCliente);

    Venta cerrarVenta(Integer idVenta);

    void eliminarVentaPorId(Integer idVenta);

    //Listar Ventas de un Cliente
    List<Venta>listarVentasDeUnCliente(Integer idCliente);

    //Listar Venta por estado
    List<Venta>listarVentasPorEstado(String estado);

    //Buscar Venta por Fecha
    List<Venta>listarVentaPorFecha(LocalDateTime inicio , LocalDateTime fin);

}
