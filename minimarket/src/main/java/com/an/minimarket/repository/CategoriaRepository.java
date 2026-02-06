package com.an.minimarket.repository;

import com.an.minimarket.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer>{
    //Buscar Por Nombre Categoria
    List<Categoria>findByNombreContainingIgnoreCase(String nombre);
    //Validar si el producto ya existe
    boolean existsByNombreIgnoreCase(String nombre);
    //Validar duplicados
    boolean existsByNombreIgnoreCaseAndIdCategoriaNot(String nombre , int idCategoria);

}
