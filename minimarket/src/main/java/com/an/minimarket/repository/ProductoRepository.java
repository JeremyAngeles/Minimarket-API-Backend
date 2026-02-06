package com.an.minimarket.repository;

import com.an.minimarket.model.Producto;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    //Listar productos Por Categoria
    List<Producto> findByCategoriaIdCategoria(Integer idCategoria);
    //Buscar Producto por Nombre
    List<Producto>findByNombreContainingIgnoreCase(String nombre);
    //Buscar Producto por Rango de Precio
    List<Producto>findByPrecioBetween(Double precioMin, Double precioMax);
    //Buscar Producto con stockBajo
    List<Producto>findByStockLessThanEqual(Integer stock);
    //Validar si el producto ya existe
    boolean existsByNombreIgnoreCase(String nombre);
    //Validar duplicados
    boolean existsByNombreIgnoreCaseAndIdProductoNot(String nombre , int idProducto);
    //Si existe el id categoria
    boolean existsByCategoriaIdCategoria(Integer idCategoria);
}
