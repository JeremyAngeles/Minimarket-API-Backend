package com.an.minimarket.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Venta {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idVenta;
        @NotNull
        private LocalDateTime fecha;
        @PositiveOrZero
        private double total;
        @NotBlank
        private String estado;
        @ManyToOne
        @JoinColumn(name = "id_cliente")
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private Cliente cliente;
}
