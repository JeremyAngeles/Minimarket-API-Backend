package com.an.minimarket.service;

import com.an.minimarket.exceptions.PeticionIncorrectaExcepcion;
import com.an.minimarket.exceptions.RecursoNoEncontradoExcepcion;
import com.an.minimarket.model.Cliente;
import com.an.minimarket.model.Venta;
import com.an.minimarket.repository.ClienteRepository;
import com.an.minimarket.repository.DetalleVentaRepository;
import com.an.minimarket.repository.VentaRepository;
import com.an.minimarket.service.Interfaz.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class  VentaService implements IVentaService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    DetalleVentaRepository detalleVentaRepository;

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta buscarVentaPorId(Integer idVenta) {

        validarId(idVenta);

        return ventaRepository.findById(idVenta).orElseThrow(() ->
                new RecursoNoEncontradoExcepcion("La venta no se pudo encontrar"));
    }

    @Override
    public Venta modificarVenta(Integer id, Venta ventaRecibido) {

        validarId(id);

        Venta venta = ventaRepository.findById(id).orElseThrow(()->
                new RecursoNoEncontradoExcepcion("La venta no existe"));

        if (ventaRecibido.getEstado() == null || ventaRecibido.getEstado().isBlank())
            throw new PeticionIncorrectaExcepcion("El estado no puede ser null , ni estar vacio");

        if (venta.getFecha().isAfter(LocalDateTime.now()))
            throw new PeticionIncorrectaExcepcion("La venta de fecha no puede ser futura");

        if ("CERRADA".equalsIgnoreCase(venta.getEstado()))
            throw  new PeticionIncorrectaExcepcion("No se pude modificar una venta Cerrada");

        if (ventaRecibido.getCliente() == null || ventaRecibido.getCliente().getIdCliente() == null)
            throw  new PeticionIncorrectaExcepcion("El cliente y su ID son obligatorios");

        validarId(ventaRecibido.getCliente().getIdCliente());

        Cliente cliente =clienteRepository.findById(
                ventaRecibido.getCliente().getIdCliente()
        ).orElseThrow(() -> new RecursoNoEncontradoExcepcion("El Cliente no se pudo encontrar"));

        venta.setEstado(ventaRecibido.getEstado());
        venta.setCliente(cliente);

        return ventaRepository.save(venta);
    }

    @Override
    public Venta agregarVenta(Integer idCliente) {

        validarId(idCliente);

        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(()->
                new RecursoNoEncontradoExcepcion("El Cliente no existe"));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        venta.setEstado("ABIERTA");
        venta.setTotal(0.0);

        return ventaRepository.save(venta);
    }

    @Override
    public Venta cerrarVenta(Integer idVenta) {

        validarId(idVenta);

        Venta venta = ventaRepository.findById(idVenta).orElseThrow(()->
                new RecursoNoEncontradoExcepcion("La venta no existe"));

        if ("CERRADA".equalsIgnoreCase(venta.getEstado()))
            throw new PeticionIncorrectaExcepcion("La venta ya esta cerrada");

        if (venta.getTotal()<=0)
            throw new PeticionIncorrectaExcepcion("No se puede cerrar una venta que el total sea 0 o sin productos");

        venta.setEstado("CERRADA");


        return ventaRepository.save(venta);
    }

    @Override
    public void eliminarVentaPorId(Integer idVenta) {

        validarId(idVenta);

        if (!ventaRepository.existsById(idVenta))
            throw new RecursoNoEncontradoExcepcion("No existe la venta a eliminar");

        if (detalleVentaRepository.existsByVentaIdVenta(idVenta))
            throw new PeticionIncorrectaExcepcion("La venta tiene productos registrados, debes vaciarla antes de eliminarla");

        ventaRepository.deleteById(idVenta);
    }

    @Override
    public List<Venta> listarVentasDeUnCliente(Integer idCliente) {

        validarId(idCliente);

        if (!clienteRepository.existsById(idCliente))
            throw  new RecursoNoEncontradoExcepcion("El Cliente no existe");

        return ventaRepository.findByClienteIdCliente(idCliente);
    }

    @Override
    public List<Venta> listarVentasPorEstado(String estado) {
        if (estado == null || estado.isBlank())
            throw  new PeticionIncorrectaExcepcion("El estado no puede estar vacio , ni ser null");

        return ventaRepository.findByEstadoContainingIgnoreCase(estado);
    }

    @Override
    public List<Venta> listarVentaPorFecha(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio == null || fin == null)
            throw  new PeticionIncorrectaExcepcion("Las fechas son obligatorias");

        if (inicio.isAfter(fin))
            throw  new PeticionIncorrectaExcepcion("La fecha de inicio no puede ser mayor a la fecha del fin");

        return ventaRepository.findByFechaBetween(inicio,fin);
    }

    private void validarId(Integer id){
        if (id == null || id<=0 )
            throw new PeticionIncorrectaExcepcion("El id no puede ser null , ni 0");
    }
}
