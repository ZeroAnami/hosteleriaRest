package com.toni.base.exception;

public class InvalidIdObjectException extends Exception {
    private String claseObjeto;
    private String clasePasada;
    private String claseEsperada;

    private InvalidIdObjectException() {
    }

    public InvalidIdObjectException(String claseObjeto) {
        this.claseObjeto = claseObjeto;
        this.clasePasada = null;
        this.claseEsperada = null;
    }

    public InvalidIdObjectException(String claseObjeto, String clasePasada, String claseEsperada) {
        this.claseObjeto = claseObjeto;
        this.clasePasada = clasePasada;
        this.claseEsperada = claseEsperada;
    }

    public String toString() {
        return this.clasePasada == null ? "La clase " + this.claseObjeto + " no tiene ningun atributo declarado como @javax.persistence.EmbeddedId" : "Buscando sobre la clase " + this.claseObjeto + " se ha intentado hacer busqueda con " + this.clasePasada + " y tiene que ser del tipo " + this.claseEsperada;
    }
}