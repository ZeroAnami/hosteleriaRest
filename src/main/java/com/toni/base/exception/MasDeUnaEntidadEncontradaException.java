package com.toni.base.exception;

import com.toni.base.DAOException;

public class MasDeUnaEntidadEncontradaException extends DAOException {
    public MasDeUnaEntidadEncontradaException() {
    }

    public MasDeUnaEntidadEncontradaException(String m) {
        super(m);
    }

    public MasDeUnaEntidadEncontradaException(Throwable t) {
        super(t);
    }

    public MasDeUnaEntidadEncontradaException(String m, Throwable t) {
        super(m, t);
    }
}
