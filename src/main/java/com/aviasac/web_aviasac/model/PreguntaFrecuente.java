package com.aviasac.web_aviasac.model;

public class PreguntaFrecuente {
    private int id;
    private String pregunta;
    private String respuesta;
    private boolean estado;
    
    public PreguntaFrecuente(int id, String pregunta, String respuesta, boolean estado) {
        this.id = id;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.estado = estado;
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getPregunta() {
        return pregunta;
    }
    
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    
    public String getRespuesta() {
        return respuesta;
    }
    
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    public boolean isEstado() {
        return estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
