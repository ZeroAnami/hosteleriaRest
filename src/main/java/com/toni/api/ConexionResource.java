package com.toni.api;

import com.toni.base.DAOException;
import com.toni.base.JacksonJsonConverter;
import com.toni.base.ResponseRest;
import com.toni.base.StatusRest;
import com.toni.model.Restaurant;
import com.toni.service.ProductService;
import com.toni.service.RestaurantService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.List;

@Path("/restaurants")
public class RestaurantResource {


    private static final Log LOGGER = LogFactory.getLog(RestaurantResource.class);

    @Inject
    private RestaurantService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProducts()  {
        try {
            List<Restaurant> list = service.findAll();
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
    public Response getProduct(@QueryParam("id") Integer id){
        try {
            Restaurant p = service.getById(id);
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
    public Response createProduct(Restaurant restaurant){
        try {
            service.guardar(restaurant);
            String response = JacksonJsonConverter.getInstance().toJson(restaurant);
            ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK, response, "Producto guardardo correctamente");
            return Response.status(StatusRest.STATUS_OK).entity(mensaje).build();
        } catch (Exception e) {
            LOGGER.error("Error al guardar los productos", e);
            ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error al guardar lost productos", e.getMessage());
            return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@QueryParam("id") Integer id, Restaurant restaurant){
        try {
            if(service.getById(id) != null){
                restaurant.setId(id);
                Restaurant p = service.modificar(restaurant);
                String response = JacksonJsonConverter.getInstance().toJson(p);
                ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK, response, "Producto actualizado correctamente");
                return Response.ok().status(Response.Status.OK).entity(mensaje).build();
            } else {
                ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "El producto no existe", "El producto no existe");
                return Response.ok().status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@QueryParam("id") Integer id){
        try {
            Restaurant restaurant = service.getById(id);
            if(restaurant != null){
                service.eliminar(restaurant);
                ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK_VACIO, "Producto borrado correctamente", "Producto borrado correctamente");
                return Response.ok().status(StatusRest.STATUS_OK_VACIO).entity(mensaje).build();
            } else {
                ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "El producto no existe", "El producto no existe");
                return Response.ok().status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }
}
