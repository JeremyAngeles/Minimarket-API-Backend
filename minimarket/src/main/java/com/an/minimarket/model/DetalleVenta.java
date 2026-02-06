package com.an.minimarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DetalleVenta {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idVentaDetalle;
        @Positive
        private int cantidad;
        @PositiveOrZero
        private double precioVenta;
        @PositiveOrZero
        private double subTotal;


        @ManyToOne
        @JoinColumn(name = "id_venta")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        private Venta venta;

        @ManyToOne
        @JoinColumn(name = "id_producto")
        @EqualsAndHashCode.Exclude
        @ToString.Exclude
        private Producto producto;

}
