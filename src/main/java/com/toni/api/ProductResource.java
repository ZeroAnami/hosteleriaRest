package com.toni.api;

import com.toni.base.DAOException;
import com.toni.base.JacksonJsonConverter;
import com.toni.base.ResponseRest;
import com.toni.base.StatusRest;
import com.toni.model.Product;
import com.toni.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.List;

@Path("/products")
public class ProductResource {


    private static final Log LOGGER = LogFactory.getLog(ProductResource.class);

    @Inject
    private ProductService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems()  {
        try {
            List<Product> list = service.findAll();
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
            Product p = service.getById(id);
            if(p == null) throw new DAOException("Producto no encontrado id "+id);
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
    public Response createItem(Product product){
        try {
            service.guardar(product);
            String response = JacksonJsonConverter.getInstance().toJson(product);
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
    public Response updateItem(@QueryParam("id") Integer id, Product product){
        try {
            if(service.getById(id) != null){
                product.setId(id);
                Product p = service.modificar(product);
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
    public Response deleteItem(@QueryParam("id") Integer id){
        try {
            Product product = service.getById(id);
            if(product != null){
                service.eliminar(product);
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
