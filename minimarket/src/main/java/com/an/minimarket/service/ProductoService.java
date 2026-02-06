package com.an.minimarket.service;

import com.an.minimarket.exceptions.PeticionIncorrectaExcepcion;
import com.an.minimarket.exceptions.RecursoNoEncontradoExcepcion;
import com.an.minimarket.model.Categoria;
import com.an.minimarket.model.Producto;
import com.an.minimarket.repository.CategoriaRepository;
import com.an.minimarket.repository.DetalleVentaRepository;
import com.an.minimarket.repository.ProductoRepository;
import com.an.minimarket.service.Interfaz.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoService implements IProductoService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    DetalleVentaRepository detalleVentaRepository;

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }


    @Override
    public Producto buscarProductoPorId(Integer idProducto) {

        validarId(idProducto);

        return productoRepository.findById(idProducto).orElseThrow( ()->
                new RecursoNoEncontradoExcepcion("No se encontro el producto Id " + idProducto));
    }

    @Override
    public Producto modificarProducto(Integer id, Producto productoRecibido) {

        validarId(id);

        Producto producto = buscarProductoPorId(id);

       validarProducto(productoRecibido);

        if (productoRepository.existsByNombreIgnoreCaseAndIdProductoNot(productoRecibido.getNombre(), id))
            throw  new PeticionIncorrectaExcepcion("El nombre ya existe " + "con el nombre " + productoRecibido.getNombre());

        Categoria categoria = categoriaRepository.findById(
                productoRecibido.getCategoria().getIdCategoria()).orElseThrow(()->
                new RecursoNoEncontradoExcepcion("El id Categoria no existe"));

        producto.setCategoria(categoria);
        producto.setNombre(productoRecibido.getNombre());
        producto.setStock(productoRecibido.getStock());
        producto.setPrecio(productoRecibido.getPrecio());


        return productoRepository.save(producto);
    }

    @Override
    public Producto agregarProducto(Producto producto) {

        validarProducto(producto);

        if (productoRepository.existsByNombreIgnoreCase(producto.getNombre()))
            throw new PeticionIncorrectaExcepcion("El nombre del producto ya existe " + producto.getNombre());

        Categoria categoria = categoriaRepository.findById(
                producto.getCategoria().getIdCategoria()).orElseThrow(()->new RecursoNoEncontradoExcepcion("Categoria no existe"));

        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }

    @Override
    public void eliminarProductoPorId(Integer idProducto) {

        validarId(idProducto);

        if (!productoRepository.existsById(idProducto))
            throw new RecursoNoEncontradoExcepcion("No existe el producto a eliminar");

        if (detalleVentaRepository.existsByProductoIdProducto(idProducto))
            throw new PeticionIncorrectaExcepcion("El producto no se puede eliminar porque tiene registros de venta asociados");

        productoRepository.deleteById(idProducto);
    }

    //Buscar Por nombre Producto
    @Override
    public List<Producto> buscarProductoPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()  )
            throw  new PeticionIncorrectaExcepcion("El nombre no puede estar vacio");

        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Producto> listarProductosPorCategoria(Integer idCategoria) {

        validarId(idCategoria);

        if (idCategoria == null)
            throw new RecursoNoEncontradoExcepcion("El idCategoria no puede ser null");

        if (!categoriaRepository.existsById(idCategoria))
            throw new RecursoNoEncontradoExcepcion("La categoría no existe");


        return productoRepository.findByCategoriaIdCategoria(idCategoria);

    }

    @Override
    public List<Producto> buscarPrecioMinMax(Double min, Double max) {
        if (min == null || max == null)
            throw  new PeticionIncorrectaExcepcion("El rango de precio es obligatorio");

        if (min < 0 || max <= 0)
            throw new PeticionIncorrectaExcepcion("El precio minimo y maximo no puede ser 0");

        if (min > max)
            throw new PeticionIncorrectaExcepcion("El precio minimo no debe superar al precio maximo");

        return productoRepository.findByPrecioBetween(min,max);
    }

    @Override
    public List<Producto> buscarStockBajo(Integer stock) {
        if (stock==null || stock<0)
            throw new PeticionIncorrectaExcepcion("El stock es obligatorio");

        return productoRepository.findByStockLessThanEqual(stock);
    }


    private void validarProducto(Producto producto){
        if (producto.getNombre() == null || producto.getNombre().isBlank())
            throw new PeticionIncorrectaExcepcion("El nombre no puede esta vacio ni ser nulo");

        if (producto.getPrecio() <= 0)
            throw  new PeticionIncorrectaExcepcion("El precio no puede ser negativo ni 0");

        if(producto.getStock() < 0 )
            throw  new PeticionIncorrectaExcepcion("El stock no puede ser negativo");

        if(producto.getCategoria() == null || producto.getCategoria().getIdCategoria() == null )
            throw  new PeticionIncorrectaExcepcion("La categoria y su id es obligatorio");
    }

    private void validarId(Integer id){
        if (id == null || id<=0 )
            throw new PeticionIncorrectaExcepcion("El id no puede ser null , ni 0");
    }

}
