package com.toni.service;

import com.toni.base.ServicioDAORestImpl;
import com.toni.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Dependent
@Transactional
public class ProductServiceImpl extends ServicioDAORestImpl<Product> implements ProductService {

    public ProductServiceImpl(){

    }

}
