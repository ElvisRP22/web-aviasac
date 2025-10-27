package com.aviasac.web_aviasac.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 200, message = "El nombre no debe superar {max} caracteres")
    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @NotBlank(message = "El username es obligatorio")
    @Size(max = 20, message = "El username no debe superar {max} caracteres")
    @Column(name = "username", length = 20, nullable = false, unique = true)
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no tiene formato válido")
    @Size(max = 150, message = "El email no debe superar {max} caracteres")
    @Column(name = "email", length = 150, nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 255, message = "La contraseña debe tener al menos {min} caracteres")
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Size(max = 9, message = "El teléfono no debe superar {max} caracteres")
    @Column(name = "telefono", length = 9, nullable = true)
    private String telefono;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Testimonio testimonio;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Solicitud> solicitudes = new ArrayList<>();

}
