package com.an.minimarket.service;

import com.an.minimarket.exceptions.PeticionIncorrectaExcepcion;
import com.an.minimarket.exceptions.RecursoNoEncontradoExcepcion;
import com.an.minimarket.model.Cliente;

import com.an.minimarket.repository.ClienteRepository;

import com.an.minimarket.repository.VentaRepository;
import com.an.minimarket.service.Interfaz.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteService implements IClienteService {


    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    VentaRepository ventaRepository;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarClientePorId(Integer idCliente) {

        validarId(idCliente);

        return clienteRepository.findById(idCliente).orElseThrow(() ->
                new RecursoNoEncontradoExcepcion("El id no se pudo encontrar " + idCliente));
    }

    @Override
    public Cliente modificarCliente(Integer id, Cliente clienteRecibido) {

        validarId(id);

        Cliente cliente = clienteRepository.findById(id).orElseThrow(()
                -> new RecursoNoEncontradoExcepcion("El Cliente no existe"));

        validarCliente(clienteRecibido);

        if (clienteRepository.existsByTelefonoIgnoreCaseAndIdClienteNot(clienteRecibido.getTelefono() , id))
            throw new PeticionIncorrectaExcepcion("El telefono ya esta registrado");

        cliente.setNombre(clienteRecibido.getNombre());
        cliente.setTelefono(clienteRecibido.getTelefono());

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente agregarCliente(Cliente cliente) {

        validarCliente(cliente);

        if (clienteRepository.existsByTelefonoIgnoreCase(cliente.getTelefono()))
            throw new PeticionIncorrectaExcepcion("El telefono ya esta registrado");

        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminarClientePorId(Integer idCliente) {

        validarId(idCliente);

        if (!clienteRepository.existsById(idCliente))
            throw  new RecursoNoEncontradoExcepcion("El cliente no existe");

        if (ventaRepository.existsByClienteIdCliente(idCliente))
            throw  new PeticionIncorrectaExcepcion("El cliente no se puede eliminar porque tiene registros de compras");

        clienteRepository.deleteById(idCliente);
    }

    @Override
    public List<Cliente> buscarNombreCliente(String nombre) {

        if (nombre == null || nombre.isBlank())
            throw  new PeticionIncorrectaExcepcion("El nombre no puede estar vacio , ni ser null");

        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Cliente> buscarPorTelefono(String telefono) {
        if (telefono == null ||  telefono.isBlank()){
            throw new PeticionIncorrectaExcepcion("El telefono es obligatorio");
        }
        return clienteRepository.findByTelefonoContainingIgnoreCase(telefono);
    }

    private void validarCliente(Cliente cliente){
        if (cliente.getNombre() == null || cliente.getNombre().isBlank())
            throw new PeticionIncorrectaExcepcion("El cliente no puede estar vacio , ni ser null");
        if (cliente.getTelefono() == null || cliente.getTelefono().isBlank())
            throw new PeticionIncorrectaExcepcion("El telefono no puede estar vacio , ni ser null");
    }

    private void validarId(Integer id){
        if (id == null || id<=0 )
            throw new PeticionIncorrectaExcepcion("El id no puede ser null , ni 0");
    }
}
