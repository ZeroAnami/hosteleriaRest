package com.toni.api;

import com.toni.base.DAOException;
import com.toni.base.JacksonJsonConverter;
import com.toni.base.ResponseRest;
import com.toni.base.StatusRest;
import com.toni.model.Etiqueta;
import com.toni.service.EtiquetaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.List;

@Path("/etiquetas")
public class EtiquetaResource {

    private static final Log LOGGER = LogFactory.getLog(EtiquetaResource.class);

    @Inject
    private EtiquetaService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems()  {
        try {
            List<Etiqueta> list = service.findAll();
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
            Etiqueta p = service.getById(id);
            if(p == null) throw new DAOException("Etiqueta no encontrada id "+id);
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
    public Response createItem(Etiqueta user){
        try {
            service.guardar(user);
            String response = JacksonJsonConverter.getInstance().toJson(user);
            ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK_REGISTRO_CREADO, response, "Etiqueta guardardo correctamente");
            return Response.status(StatusRest.STATUS_OK_REGISTRO_CREADO).entity(mensaje).build();
        } catch (Exception e) {
            LOGGER.error("Error al guardar los usuarios", e);
            ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error al guardar las etiquetas", e.getMessage());
            return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateItem(Etiqueta user){
        try {
            if(service.getById(user.getId()) != null){
                Etiqueta p = service.modificar(user);
                String response = JacksonJsonConverter.getInstance().toJson(p);
                ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK, response, "Etiqueta actualizada correctamente");
                return Response.ok().status(Response.Status.OK).entity(mensaje).build();
            } else {
                ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "La etiqueta no existe", "La etiqueta no existe");
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
            Etiqueta p = service.getById(id);
            if(p != null){
                service.eliminar(p);
                ResponseRest mensaje = new ResponseRest(StatusRest.STATUS_OK_VACIO, "Etiqueta borrada correctamente", "Etiqueta borrada correctamente");
                return Response.ok().status(StatusRest.STATUS_OK_VACIO).entity(mensaje).build();
            } else {
                ResponseRest mensajeError = new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "La etiqueta no existe", "La etiqueta no existe");
                return Response.ok().status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(mensajeError).build();
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Response.status(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO).entity(new ResponseRest(StatusRest.STATUS_ERROR_CLIENTE_RECURSO_NO_ENCONTRADO, "Error", "")).build();
    }
}
