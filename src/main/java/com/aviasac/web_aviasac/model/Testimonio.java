package com.aviasac.web_aviasac.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "testimonios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Testimonio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 255, message = "La descripción debe ser breve")
    @Column(name = "comentario", length = 255, nullable = false)
    private String comentario;
    
    @Min(1) @Max(5)
    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDate.now();
        if(this.calificacion == null) this.calificacion = 5;
    }
}