/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.RolEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.PermisoService;
import com.j2km.inmueblesgo.service.RolService;
import com.j2km.inmueblesgo.service.UsuarioService;
import com.j2km.inmueblesgo.web.generic.GenericLazyDataModel;
import com.sun.imageio.plugins.jpeg.JPEG;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author jkelsy
 */

@ViewScoped
@Named("usuarioBean")
public class UsuarioBean implements Serializable{
    
    private static final Logger logger = Logger.getLogger(UsuarioBean.class.getName());
    
    @Inject 
    private UsuarioService usuarioService;
    
    @Inject
    private RolService rolService;
    
    @Inject 
    private PermisoService permisoService;
    
    private GenericLazyDataModel<UsuarioEntity> lazyModel;    
    
    private UsuarioEntity usuario;  
    private Boolean esVendedor;
    private Boolean esAdmin;    
    private String login;
    
    public GenericLazyDataModel<UsuarioEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(usuarioService);
        }
        return this.lazyModel;
    }
    
    public void nuevoUsuario(){        
        
        try{
            PermisoEntity permiso;
            RolEntity rolVendedor = rolService.findByNombre("VENDEDOR");
            RolEntity rolAdmin = rolService.findByNombre("ADMIN");
            
            if(usuario.getId() == null){
                usuario = usuarioService.save(usuario);                
            }else{
                usuarioService.update(usuario); 
            }   
            
            if(esVendedor){                
                permiso = new PermisoEntity();
                permiso.setRol(rolVendedor);
                permiso.setUsuario(usuario);
                permisoService.save(permiso);
            }else{
                permiso = permisoService.findByUsuarioAndRol(usuario, rolVendedor);
                if(permiso != null){
                    permisoService.delete(permiso);
                }                
            }

            if(esAdmin){                
                permiso = new PermisoEntity();
                permiso.setRol(rolAdmin);
                permiso.setUsuario(usuario);
                permisoService.save(permiso);
            }else{
                permiso = permisoService.findByUsuarioAndRol(usuario, rolAdmin);
                if(permiso != null){
                    permisoService.delete(permiso);
                } 
            }

            FacesMessage facesMsg = new FacesMessage(
                                FacesMessage.SEVERITY_INFO, 
                                "Usuario Guardado Exitosamente", 
                                "Usuario Guardado Exitosamente");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);  

            reset();
        }catch(PersistenceException e){
            FacesMessage facesMsg = new FacesMessage(
                                FacesMessage.SEVERITY_ERROR, 
                                "Error al crear usuario", 
                                "Error");
            FacesContext.getCurrentInstance().addMessage(null, facesMsg);   
            
            logger.log(Level.SEVERE, "Error al guardar usuario", e);
            
            System.out.println("guardar usuario");
        }
    }
            
    public void reset(){
        usuario = new UsuarioEntity();       
        esVendedor = false;
        esAdmin = false;   
    }  

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public Boolean getEsVendedor() {
        return esVendedor;
    }

    public void setEsVendedor(Boolean esVendedor) {
        this.esVendedor = esVendedor;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public List<UsuarioEntity> getUsuarios() {
        return usuarioService.findAllUsuarioEntities();
    }  

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }    
    
    public void seleccionarUsuario(Long usuarioId) {   
        usuario = usuarioService.find(usuarioId);        
        esAdmin = usuarioService.esAdmin(usuario);
        esVendedor = usuarioService.esVendedor(usuario);
    }
    
    public void onRowSelect(SelectEvent event) {        
        seleccionarUsuario(((UsuarioEntity)event.getObject()).getId());
    }
    
    
}
