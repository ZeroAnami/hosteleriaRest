package com.toni.service;

import com.toni.base.ServicioDAORestImpl;
import com.toni.model.Order;
import com.toni.model.User;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Dependent
@Transactional
public class OrderServiceImpl extends ServicioDAORestImpl<Order> implements OrderService {

    public OrderServiceImpl(){

    }

}
