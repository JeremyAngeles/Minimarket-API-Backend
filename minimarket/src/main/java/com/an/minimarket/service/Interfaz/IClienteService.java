package com.an.minimarket.service.Interfaz;

import com.an.minimarket.model.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente>listarClientes();

    Cliente buscarClientePorId(Integer idCliente);

    Cliente modificarCliente(Integer id , Cliente clienteRecibido);

    Cliente agregarCliente(Cliente cliente);

    void eliminarClientePorId(Integer idCliente);

    List<Cliente>buscarNombreCliente(String nombre);

    List<Cliente>buscarPorTelefono(String telefono);
}
