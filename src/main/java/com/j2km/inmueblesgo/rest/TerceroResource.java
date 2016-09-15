package com.j2km.inmueblesgo.rest;

import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.service.TerceroService;

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

@Path("/terceros")
@Named
public class TerceroResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private TerceroService terceroService;
    
    /**
     * Get the complete list of Tercero Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /terceros
     * @return List of TerceroEntity (JSON)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TerceroEntity> getAllTerceros() {
        return terceroService.findAllTerceroEntities();
    }
    
    /**
     * Get the number of Tercero Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /terceros/count
     * @return Number of TerceroEntity
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getCount() {
        return terceroService.countAllEntries();
    }
    
    /**
     * Get a Tercero Entity <br/>
     * HTTP Method: GET <br/>
     * Example URL: /terceros/3
     * @param id
     * @return A Tercero Entity (JSON)
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TerceroEntity getTerceroById(@PathParam("id") Long id) {
        return terceroService.find(id);
    }
    
    /**
     * Create a Tercero Entity <br/>
     * HTTP Method: POST <br/>
     * POST Request Body: New TerceroEntity (JSON) <br/>
     * Example URL: /terceros
     * @param tercero
     * @return A TerceroEntity (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TerceroEntity addTercero(TerceroEntity tercero) {
        return terceroService.save(tercero);
    }
    
    /**
     * Update an existing Tercero Entity <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: Updated TerceroEntity (JSON) <br/>
     * Example URL: /terceros
     * @param tercero
     * @return A TerceroEntity (JSON)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TerceroEntity updateTercero(TerceroEntity tercero) {
        return terceroService.update(tercero);
    }
    
    /**
     * Delete an existing Tercero Entity <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /terceros/3
     * @param id
     */
    @Path("{id}")
    @DELETE
    public void deleteTercero(@PathParam("id") Long id) {
        TerceroEntity tercero = terceroService.find(id);
        terceroService.delete(tercero);
    }
    
}
