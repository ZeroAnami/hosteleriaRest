package com.toni.service;

import com.toni.base.ServicioDAORestImpl;
import com.toni.model.Product;
import com.toni.model.Restaurant;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Dependent
@Transactional
public class RestaurantServiceImpl extends ServicioDAORestImpl<Restaurant> implements RestaurantService {

    public RestaurantServiceImpl(){

    }

}
