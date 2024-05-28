package com.toni.base;

public class ResponseRest {
    private Integer codigoStatus;
    private String mensaje;
    private String descripcion;

    public ResponseRest() {
    }

    public ResponseRest(Integer codigoStatus, String mensaje, String descripcion) {
        this.codigoStatus = codigoStatus;
        this.mensaje = mensaje;
        this.descripcion = descripcion;
    }

    public Integer getCodigoStatus() {
        return this.codigoStatus;
    }

    public void setCodigoStatus(Integer codigoStatus) {
        this.codigoStatus = codigoStatus;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
