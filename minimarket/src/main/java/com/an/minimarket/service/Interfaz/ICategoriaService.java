package com.an.minimarket.service.Interfaz;

import com.an.minimarket.model.Categoria;

import java.util.List;

public interface ICategoriaService {
     List<Categoria>listarCategorias();

     Categoria modificarCategoria(Integer id,Categoria categoriaRecibida);

     Categoria buscarCategoriaPorId(Integer idCategoria);

     Categoria agregarCategoria(Categoria categoria);

     void eliminarCategoriaPorId(Integer idCategoria);

     //Buscar Por nombre Categoria
     List<Categoria>buscarCategoriaPorNombre(String nombre);
}
