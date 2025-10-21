package com.aviasac.web_aviasac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_de_cultivo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoDeCultivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del tipo de cultivo es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar {max} caracteres")
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
}
