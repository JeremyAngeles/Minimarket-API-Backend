package com.an.minimarket.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Categoria {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idCategoria;
        @NotBlank
        private String nombre;
        @OneToMany(mappedBy = "categoria")
        @JsonIgnore
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private List<Producto>productos;
}
