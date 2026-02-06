package com.an.minimarket.service.Interfaz;

import com.an.minimarket.model.Producto;

import java.util.List;

public interface IProductoService {
    List<Producto> listarProductos();

    Producto buscarProductoPorId(Integer idProducto);

    Producto modificarProducto(Integer id , Producto productoRecibido);

    Producto agregarProducto(Producto producto);

    void eliminarProductoPorId(Integer idProducto);

    //Buscar Producto Por Nombre
    List<Producto>buscarProductoPorNombre(String nombre);
    //Listar Producto por Categoria
    List<Producto>listarProductosPorCategoria(Integer idCategoria);
    //Listar Precio de Producto min - max
    List<Producto>buscarPrecioMinMax(Double min , Double max);
    //Buscar Producto con stockBajo
    List<Producto>buscarStockBajo(Integer stock);

}
