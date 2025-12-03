package com.aviasac.web_aviasac.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "solicitudes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Usuario se establecerá en el controller, no validar aquí
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Servicio se establecerá en el controller, no validar aquí
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    // Tipo de cultivo se establecerá en el controller, no validar aquí
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_de_cultivo", nullable = false)
    private TipoDeCultivo tipoDeCultivo;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(max = 255, message = "La ubicación no debe superar 255 caracteres")
    @Column(name = "ubicacion", length = 255, nullable = false)
    private String ubicacion;

    @NotNull(message = "El número de hectáreas es obligatorio")
    @Min(value = 1, message = "Debe ser al menos 1 hectárea")
    @Column(name = "num_hectareas", nullable = false)
    private Integer numHectareas;

    @Size(max = 100, message = "El área para aterrizar no debe superar 100 caracteres")
    @Column(name = "area_para_aterrizar", length = 100)
    private String areaParaAterrizar;

    @Size(max = 255, message = "El mensaje no debe superar 255 caracteres")
    @Column(name = "mensaje", length = 255)
    private String mensaje;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    private SolicitudEstado estado;

    // Quitar @FutureOrPresent temporalmente para debugging
    @Column(name = "fecha_preferida")
    private LocalDate fechaPreferida;

    @PrePersist
    private void prePersist() {
        if (this.fechaSolicitud == null) {
            this.fechaSolicitud = LocalDateTime.now();
        }
        if (this.estado == null) {
            this.estado = SolicitudEstado.PENDIENTE;
        }
    }
}