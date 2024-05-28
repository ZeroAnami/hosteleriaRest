package com.toni.base;

public class StatusRest {
    public static final int STATUS_OK = 200;
    public static final int STATUS_OK_REGISTRO_CREADO = 201;
    public static final int STATUS_OK_CON_ERRORES = 202;
    public static final int STATUS_OK_VACIO = 204;
    public static final int STATUS_OK_INFO = 101;
    public static final int STATUS_ERROR_CLIENTE = 400;
    public static final int STATUS_ERROR_FORBIDDEN = 403;
    public static final int STATUS_PASSWORD_OUTDATED = 413;
    public static final int STATUS_USUARIO_SIN_PERMISOS = 414;
    public static final int STATUS_USUARIO_BLOQUEADO = 415;
    public static final int STATUS_USUARIO_NO_EXISTE = 416;
    public static final int STATUS_USUARIO_INVALID_PASSWORD = 417;
    public static final int STATUS_RESPONSE_REST = 450;
    public static final int STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO = 404;
    public static final int STATUS_ERROR_CLIENTE_TIPO_MIME_NO_SOPORTADO = 415;
    public static final int STATUS_ERROR_SERVIDOR = 500;

    public StatusRest() {
    }
}