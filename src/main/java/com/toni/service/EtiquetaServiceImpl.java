package com.toni.service;

import com.toni.base.ServicioDAORestImpl;
import com.toni.model.Categoria;
import com.toni.model.User;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Dependent
@Transactional
public class CategoriaServiceImpl extends ServicioDAORestImpl<Categoria> implements CategoriaService {

    public CategoriaServiceImpl(){

    }

}
