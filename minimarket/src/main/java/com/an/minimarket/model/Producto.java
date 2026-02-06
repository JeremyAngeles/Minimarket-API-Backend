package com.an.minimarket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Producto {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idProducto;
        @NotBlank
        private String nombre;
        @Min(0)
        private double precio;
        @PositiveOrZero
        private double stock;

        @ManyToOne
        @JoinColumn(name = "id_categoria")
        //@JsonIgnore
        private Categoria  categoria;
}
