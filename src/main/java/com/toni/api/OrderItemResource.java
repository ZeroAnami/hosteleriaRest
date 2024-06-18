package com.toni.api;

import com.toni.base.DAOException;
import com.toni.base.JacksonJsonConverter;
import com.toni.base.ResponseRest;
import com.toni.base.StatusRest;
import com.toni.model.Order;
import com.toni.service.ConexionService;
import com.toni.service.OrderService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.List;

@Path("/order")
public class OrderResource {

    private static final Log LOGGER = LogFactory.getLog(OrderResource.class);

    @Inject
    private OrderService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems()  {
        try {
            List<Order> list = service.findAll();
            String response = JacksonJsonConverter.getInstance().toJson(list);
            return Response.status(StatusRest.STATUS_OK).entity(new ResponseRest(StatusRest.STATUS_OK, response, "Correcto")).build();
        } catch (DAOException ex) {
            LOGGER.error(ex);
        } catch (IOException ex) {
            LOGGER.error(ex);
        }

        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }

    @Path("/getById")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemById(@QueryParam("id") Integer id){
        try {
            Order p = service.getById(id);
            if(p == null) throw new DAOException("Order no encontrado id "+id);
            String response = JacksonJsonConverter.getInstance().toJson(p);
            return Response.status(StatusRest.STATUS_OK).entity(new ResponseRest(StatusRest.STATUS_OK, response, "Correcto")).build();
        } catch (DAOException e) {
            LOGGER.error(e);
        } catch (IOException e) {
            LOGGER.error(e);
        }

        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(Order order){
        try {
            service.guardar(order);
            String response = JacksonJsonConverter.getInstance().toJson(order);
            ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK_REGISTRO_CREADO, response, "Order guardarda correctamente");
            return Response.status(StatusRest.STATUS_OK_REGISTRO_CREADO).entity(mensaje).build();
        } catch (Exception e) {
            LOGGER.error("Error al guardar los productos", e);
            ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error al guardar las conexiones", e.getMessage());
            return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(@QueryParam("id") Integer id, Order order){
        try {
            if(service.getById(id) != null){
                order.setId(id);
                Order p = service.modificar(order);
                String response = JacksonJsonConverter.getInstance().toJson(p);
                ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK, response, "Order actualizado correctamente");
                return Response.ok().status(Response.Status.OK).entity(mensaje).build();
            } else {
                ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "La order no existe", "La order no existe");
                return Response.ok().status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteItem(@QueryParam("id") Integer id){
        try {
            Order order = service.getById(id);
            if(order != null){
                service.eliminar(order);
                ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK_VACIO, "Order borrado correctamente", "Order borrado correctamente");
                return Response.ok().status(StatusRest.STATUS_OK_VACIO).entity(mensaje).build();
            } else {
                ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Order no existe", "Order no existe");
                return Response.ok().status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }
}
