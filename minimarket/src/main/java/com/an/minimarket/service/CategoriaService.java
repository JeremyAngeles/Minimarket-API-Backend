package com.an.minimarket.service;

import com.an.minimarket.exceptions.PeticionIncorrectaExcepcion;
import com.an.minimarket.exceptions.RecursoNoEncontradoExcepcion;
import com.an.minimarket.model.Categoria;
import com.an.minimarket.repository.CategoriaRepository;
import com.an.minimarket.repository.ProductoRepository;
import com.an.minimarket.service.Interfaz.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria modificarCategoria(Integer id , Categoria categoriaRecibida) {
        validarId(id);

        Categoria categoria = categoriaRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoExcepcion("La categoria no existe"));

        validarNombre(categoriaRecibida.getNombre());

        if (categoriaRepository.existsByNombreIgnoreCaseAndIdCategoriaNot(categoriaRecibida.getNombre(),id))
            throw new PeticionIncorrectaExcepcion("El nombre de la categoria ya existe: " + categoriaRecibida.getNombre());

        categoria.setNombre(categoriaRecibida.getNombre());

        return this.categoriaRepository.save(categoria);
    }

    @Override
    public Categoria buscarCategoriaPorId(Integer idCategoria) {

        validarId(idCategoria);

        return categoriaRepository.findById(idCategoria).orElseThrow(()-> new RecursoNoEncontradoExcepcion("La Categoria no existe"));
    }

    @Override
    public Categoria agregarCategoria(Categoria categoria) {

        validarNombre(categoria.getNombre());

        if (categoriaRepository.existsByNombreIgnoreCase(categoria.getNombre()))
            throw new PeticionIncorrectaExcepcion("El nombre de la categoria ya existe: " + categoria.getNombre());

        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarCategoriaPorId(Integer idCategoria) {

        validarId(idCategoria);

        if (!categoriaRepository.existsById(idCategoria))
            throw new RecursoNoEncontradoExcepcion("El id categoria no existe");

        if (productoRepository.existsByCategoriaIdCategoria(idCategoria))
            throw new PeticionIncorrectaExcepcion("No puedes eliminar la categoria, porque tiene productos adentro , tienes que borrar los productos primero");

        categoriaRepository.deleteById(idCategoria);
    }


    //Buscar Por Nombre Categoria
    @Override
    public List<Categoria> buscarCategoriaPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw  new PeticionIncorrectaExcepcion("El nombre a buscar no puede estar vacio ni nulo");
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    private void validarId(Integer id){
        if (id == null || id<=0 )
            throw new PeticionIncorrectaExcepcion("El id no puede ser null , ni 0");
    }

    private void validarNombre(String nombre){
        if (nombre == null || nombre.isBlank())
            throw new PeticionIncorrectaExcepcion("El nombre de la categoria no puede ser vacio ni nulo");
    }

}
