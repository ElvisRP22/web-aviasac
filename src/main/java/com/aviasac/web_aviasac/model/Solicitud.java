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

    @NotNull(message = "El usuario es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @NotNull(message = "El servicio es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @NotNull(message = "El tipo de cultivo es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_de_cultivo", nullable = false)
    private TipoDeCultivo tipoDeCultivo;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(max = 255)
    @Column(name = "ubicacion", length = 255, nullable = false)
    private String ubicacion;

    @NotNull(message = "El número de hectáreas es obligatorio")
    @PositiveOrZero
    @Column(name = "num_hectareas", nullable = false)
    private Integer numHectareas;

    @Size(max = 100)
    @Column(name = "area_para_aterrizar", length = 100)
    private String areaParaAterrizar;

    @Size(max = 255)
    @Column(name = "mensaje", length = 255)
    private String mensaje;

    @NotNull
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    private SolicitudEstado estado;

    @FutureOrPresent(message = "La fecha preferida debe ser hoy o en el futuro")
    @Column(name = "fecha_preferida")
    private LocalDate fechaPreferida;

    @PrePersist
    private void prePersist() {
        this.fechaSolicitud = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = SolicitudEstado.PENDIENTE;
        }
    }
}