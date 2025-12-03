package com.aviasac.web_aviasac.model;

public enum SolicitudEstado {
    PENDIENTE("Pendiente"),
    EN_PROCESO("En Proceso"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada");

    private final String descripcion;

    SolicitudEstado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}