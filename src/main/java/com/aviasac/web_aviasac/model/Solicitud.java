package com.aviasac.web_aviasac.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Size(max = 255, message = "La ubicación no debe superar {max} caracteres")
    @Column(name = "ubicacion", length = 255, nullable = false)
    private String ubicacion;

    @NotNull(message = "El número de hectáreas es obligatorio")
    @PositiveOrZero(message = "El número de hectáreas debe ser cero o positivo")
    @Column(name = "num_hectareas", nullable = false)
    private Integer numHectareas;

    @Size(max = 100, message = "El área para aterrizar no debe superar {max} caracteres")
    @Column(name = "area_para_aterrizar", length = 100)
    private String areaParaAterrizar;

    @Size(max = 255, message = "El mensaje no debe superar {max} caracteres")
    @Column(name = "mensaje", length = 255)
    private String mensaje;

    @NotNull(message = "La fecha de solicitud es obligatoria")
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 20, nullable = false)
    private SolicitudEstado estado;

    @FutureOrPresent(message = "La fecha preferida debe ser hoy o en el futuro")
    @Column(name = "fecha_preferida")
    private LocalDateTime fechaPreferida;

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

