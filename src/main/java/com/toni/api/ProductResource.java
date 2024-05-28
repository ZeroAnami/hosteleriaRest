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
    public Response getProducts()  {
        try {
            List<Product> list = service.findAll();
            String response = JacksonJsonConverter.getInstance().toJson(list);
            return Response.status(StatusRest.STATUS_OK).entity(new ResponseRest(StatusRest.STATUS_OK, "Correcto", response)).build();
        } catch (DAOException ex) {
            LOGGER.error(ex);
        } catch (IOException ex) {
            LOGGER.error(ex);
        }

        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") Integer id){
        try {
            Product p = service.getById(id);
            String response = JacksonJsonConverter.getInstance().toJson(p);
            return Response.status(StatusRest.STATUS_OK).entity(new ResponseRest(StatusRest.STATUS_OK, "Correcto", response)).build();
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
    public Response createProduct(Product product){
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

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(@PathParam("id") Integer id, Product product){
        try {
            if(service.getById(id) != null){
                product.setId(id);
                Product p = service.modificar(product);
                return Response.ok().status(Response.Status.OK).entity(p).build();
            } else {
                ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "El producto no existe", "El producto no existe");
                return Response.ok().status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }

    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") Integer id){
        try {
            Product product = service.getById(id);
            if(product != null){
                service.eliminar(product);
                ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_OK_VACIO, "Producto borrado correctamente", "Producto borrado correctamente");
                return Response.ok().status(StatusRest.STATUS_OK_VACIO).entity(mensajeError).build();
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
