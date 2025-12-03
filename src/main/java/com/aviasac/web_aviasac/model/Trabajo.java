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
@Table(name = "trabajos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El título del trabajo es obligatorio")
    @Size(max = 200, message = "El título no debe superar {max} caracteres")
    @Column(name = "titulo_trabajo", length = 200, nullable = false)
    private String tituloTrabajo;

    @NotBlank(message = "La descripción es obligatoria")
	@Size(max = 5000, message = "La descripción no debe superar {max} caracteres")
	@Lob
	@Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Size(max = 255, message = "La URL del archivo no debe superar {max} caracteres")
    @Column(name = "url_file", length = 255, nullable = false)
    private String urlFile;
}
