package com.j2km.inmueblesgo.rest;

import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.service.TipoIdentificacionService;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tipoIdentificacions")
@Named
public class TipoIdentificacionResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private TipoIdentificacionService tipoIdentificacionService;
    
    /**
     * Get the complete list of TipoIdentificacion Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /tipoIdentificacions
     * @return List of TipoIdentificacionEntity (JSON)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoIdentificacionEntity> getAllTipoIdentificacions() {
        return tipoIdentificacionService.findAllTipoIdentificacionEntities();
    }
    
    /**
     * Get the number of TipoIdentificacion Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /tipoIdentificacions/count
     * @return Number of TipoIdentificacionEntity
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getCount() {
        return tipoIdentificacionService.countAllEntries();
    }
    
    /**
     * Get a TipoIdentificacion Entity <br/>
     * HTTP Method: GET <br/>
     * Example URL: /tipoIdentificacions/3
     * @param id
     * @return A TipoIdentificacion Entity (JSON)
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TipoIdentificacionEntity getTipoIdentificacionById(@PathParam("id") Long id) {
        return tipoIdentificacionService.find(id);
    }
    
    /**
     * Create a TipoIdentificacion Entity <br/>
     * HTTP Method: POST <br/>
     * POST Request Body: New TipoIdentificacionEntity (JSON) <br/>
     * Example URL: /tipoIdentificacions
     * @param tipoIdentificacion
     * @return A TipoIdentificacionEntity (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TipoIdentificacionEntity addTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        return tipoIdentificacionService.save(tipoIdentificacion);
    }
    
    /**
     * Update an existing TipoIdentificacion Entity <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: Updated TipoIdentificacionEntity (JSON) <br/>
     * Example URL: /tipoIdentificacions
     * @param tipoIdentificacion
     * @return A TipoIdentificacionEntity (JSON)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TipoIdentificacionEntity updateTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        return tipoIdentificacionService.update(tipoIdentificacion);
    }
    
    /**
     * Delete an existing TipoIdentificacion Entity <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /tipoIdentificacions/3
     * @param id
     */
    @Path("{id}")
    @DELETE
    public void deleteTipoIdentificacion(@PathParam("id") Long id) {
        TipoIdentificacionEntity tipoIdentificacion = tipoIdentificacionService.find(id);
        tipoIdentificacionService.delete(tipoIdentificacion);
    }
    
}
