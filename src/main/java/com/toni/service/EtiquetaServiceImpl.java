package com.toni.service;

import com.toni.base.ServicioDAORestImpl;
import com.toni.model.Etiqueta;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Dependent
@Transactional
public class EtiquetaServiceImpl extends ServicioDAORestImpl<Etiqueta> implements EtiquetaService {

    public EtiquetaServiceImpl(){

    }

}
