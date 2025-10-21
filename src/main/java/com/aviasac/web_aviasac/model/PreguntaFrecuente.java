package com.aviasac.web_aviasac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "preguntas_frecuentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaFrecuente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "La pregunta es obligatoria")
    @Size(max = 255, message = "La pregunta no debe superar {max} caracteres")
    @Column(name = "pregunta", length = 255, nullable = false)
    private String pregunta;

    @NotBlank(message = "La respuesta es obligatoria")
    @Size(max = 5000, message = "La respuesta no debe superar {max} caracteres")
    @Lob
    @Column(name = "respuesta", nullable = false)
    private String respuesta;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
