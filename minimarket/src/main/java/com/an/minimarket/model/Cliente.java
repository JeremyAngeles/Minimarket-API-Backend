package com.an.minimarket.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Cliente {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idCliente;
        @NotBlank
        private String nombre;
        @NotBlank
        @Size(min = 9,max = 9)
        private String telefono;
        @OneToMany(mappedBy = "cliente")
        @JsonIgnore
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private List<Venta> ventas;

}
