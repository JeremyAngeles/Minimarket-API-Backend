package com.an.minimarket.service;

import com.an.minimarket.exceptions.PeticionIncorrectaExcepcion;
import com.an.minimarket.exceptions.RecursoNoEncontradoExcepcion;
import com.an.minimarket.model.DetalleVenta;
import com.an.minimarket.model.Producto;
import com.an.minimarket.model.Venta;
import com.an.minimarket.repository.DetalleVentaRepository;
import com.an.minimarket.repository.ProductoRepository;
import com.an.minimarket.repository.VentaRepository;
import com.an.minimarket.service.Interfaz.IDetalleVentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DetalleVentaService implements IDetalleVentaService {

    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    DetalleVentaRepository detalleVentaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public List<DetalleVenta> listarDetalleVentas() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public DetalleVenta buscarDetalleVentaPorId(Integer idDetalleVenta) {

        validarId(idDetalleVenta);

        return detalleVentaRepository.findById(idDetalleVenta).orElseThrow(() ->
                new RecursoNoEncontradoExcepcion("El id detalle no existe"));
    }

    @Transactional
    @Override
    public DetalleVenta agregarDetalleVenta(DetalleVenta detalleVenta) {

        if (detalleVenta.getCantidad() <=0)
            throw new PeticionIncorrectaExcepcion("La cantidad debe ser mayora a 0");

        if (detalleVenta.getVenta() == null)
            throw new PeticionIncorrectaExcepcion("El objeto venta no puede ser nulo");

        validarId(detalleVenta.getVenta().getIdVenta());

        if (detalleVenta.getProducto() == null)
            throw new PeticionIncorrectaExcepcion("El objeto producto no puede ser nulo");

        validarId(detalleVenta.getProducto().getIdProducto());



        Venta venta = ventaRepository.findById(
                detalleVenta.getVenta().getIdVenta()
        ).orElseThrow(()->new RecursoNoEncontradoExcepcion("La venta no se encontro"));

        Producto producto = productoRepository.findById(
                detalleVenta.getProducto().getIdProducto()
        ).orElseThrow(()-> new RecursoNoEncontradoExcepcion("El producto no existe"));

        if ("CERRADA".equalsIgnoreCase(venta.getEstado()))
            throw new PeticionIncorrectaExcepcion("No se puede modificar una venta cerrada");


        if (producto.getStock() < detalleVenta.getCantidad())
            throw new PeticionIncorrectaExcepcion("Stock insuficiente");

        detalleVenta.setPrecioVenta(producto.getPrecio());

        double subTotalCalculado =detalleVenta.getCantidad() * producto.getPrecio();
        detalleVenta.setSubTotal(subTotalCalculado);

        producto.setStock(producto.getStock() - detalleVenta.getCantidad());
        productoRepository.save(producto);

        venta.setTotal(venta.getTotal() + subTotalCalculado);
        ventaRepository.save(venta);

        detalleVenta.setVenta(venta);
        detalleVenta.setProducto(producto);

        return detalleVentaRepository.save(detalleVenta);
    }

    @Transactional
    @Override
    public void eliminarDetalleVentaPorId(Integer idDetalleVenta) {
        validarId(idDetalleVenta);

        DetalleVenta detalleVenta = detalleVentaRepository.findById(idDetalleVenta).orElseThrow(() ->
                new RecursoNoEncontradoExcepcion("No existe el id Detalle Venta"));

        Venta venta =detalleVenta.getVenta();
        Producto producto = detalleVenta.getProducto();

        if ("CERRADA".equalsIgnoreCase(venta.getEstado()))
            throw new PeticionIncorrectaExcepcion("No se puede eliminar detalle de una ventana cerrada");

        producto.setStock(producto.getStock() + detalleVenta.getCantidad());
        productoRepository.save(producto);

        double nuevoTotal = venta.getTotal() - detalleVenta.getSubTotal();

        if (nuevoTotal<0 ) nuevoTotal = 0.0;

        venta.setTotal(nuevoTotal);
        ventaRepository.save(venta);

        detalleVentaRepository.deleteById(idDetalleVenta);
    }

    @Override
    public List<DetalleVenta> listarDetallesDeVenta(Integer idVenta) {

        validarId(idVenta);

        if (!ventaRepository.existsById(idVenta))
            throw  new RecursoNoEncontradoExcepcion("La Venta no existe");

        return detalleVentaRepository.findByVentaIdVenta(idVenta);
    }

    private void validarId(Integer id){
        if (id==null || id<=0)
            throw new PeticionIncorrectaExcepcion("El id no puede ser null, ni 0");
    }
}
