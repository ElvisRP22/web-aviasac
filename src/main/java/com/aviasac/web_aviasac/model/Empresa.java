package com.aviasac.web_aviasac.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El RUC es obligatorio")
    @Size(max = 11, message = "El RUC no debe superar {max} caracteres")
    @Pattern(regexp = "[0-9-]+", message = "El RUC sólo puede contener dígitos y guiones")
    @Column(name = "ruc", length = 11, nullable = false, unique = true)
    private String ruc;

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 255, message = "La razón social no debe superar {max} caracteres")
    @Column(name = "razon_social", length = 255, nullable = false)
    private String razonSocial;

    @Size(max = 255, message = "El nombre comercial no debe superar {max} caracteres")
    @Column(name = "nombre_comercial", length = 255, nullable = false)
    private String nombreComercial;

    @Size(max = 255, message = "La dirección no debe superar {max} caracteres")
    @Column(name = "direccion", length = 255, nullable = false)
    private String direccion;

    @Size(max = 9, message = "El teléfono no debe superar {max} caracteres")
    @Pattern(regexp = "[0-9()+\\-\\s]+", message = "El teléfono contiene caracteres no válidos")
    @Column(name = "telefono", length = 9, nullable = false)
    private String telefono;

    @Email(message = "El correo no tiene formato válido")
    @Size(max = 150, message = "El correo no debe superar {max} caracteres")
    @Column(name = "email", length = 150)
    private String email;

    @Size(max = 200, message = "El horario no debe superar {max} caracteres")
    @Column(name = "horario_atencion", length = 200)
    private String horarioAtencion;

}
