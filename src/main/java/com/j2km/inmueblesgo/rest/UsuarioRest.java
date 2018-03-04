package com.j2km.inmueblesgo.rest;

import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.EncriptarService;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("usuarioservice")
public class UsuarioRest {
    
    @Inject private UsuarioRepository ur;
    @Inject private EncriptarService encriptarService;
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String login(@FormParam("login") String login,
                        @FormParam("password") String password)
    {
        String result = "false";
        UsuarioEntity usuario = ur.findOptionalByLogin(login);
        
        if(usuario != null){
            if(usuario.getPassword().equals(encriptarService.encriptar(password))){
                result = "true";
            }            
        }        
        return result;
    }
    
}
