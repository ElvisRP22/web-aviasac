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
@Table(name = "servicios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 200, message = "El nombre no debe superar {max} caracteres")
	@Column(name = "nombre", length = 200, nullable = false)
	private String nombre;

	@NotBlank(message = "La descripción es obligatoria")
	@Size(max = 5000, message = "La descripción no debe superar {max} caracteres")
	@Lob
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Size(max = 255, message = "La URL de la imagen no debe superar {max} caracteres")
	@Column(name = "url_imagen", length = 255, nullable = false)
	private String urlImagen;
}
