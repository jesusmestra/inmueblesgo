package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.TerceroRepository;
import com.j2km.inmueblesgo.service.TipoIdentificacionService;
import com.j2km.inmueblesgo.service.UsuarioService;
import com.j2km.inmueblesgo.web.util.MessageFactory;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import org.primefaces.event.SelectEvent;

@Named("terceroBean")
@ViewScoped
public class TerceroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TerceroBean.class.getName());


    private TerceroEntity tercero;

    @Inject
    private TerceroRepository terceroService;
    
    @Inject UsuarioService usuarioService;

    @Inject
    private TipoIdentificacionService tipoIdentificacionService;

    private List<TipoIdentificacionEntity> allTipoIdentificacionsList;
    
    private List<TerceroEntity> allTerceros;
    
    private List<TerceroEntity> tercerosPorUsuario;

    public void prepareNewTercero() {
        reset();
        this.tercero = new TerceroEntity();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }

    public List<TerceroEntity> getTercerosPorUsuario() {
        
        tercerosPorUsuario = terceroService.findByUsuario(
                usuarioService.findByLogin(                        
                    FacesContext.
                        getCurrentInstance().
                        getExternalContext().
                        getRemoteUser()
                )
        );
        return tercerosPorUsuario;
    }

    public void setTercerosPorUsuario(List<TerceroEntity> tercerosPorUsuario) {
        this.tercerosPorUsuario = tercerosPorUsuario;
    }    

    public List<TerceroEntity> getAllTerceros() {        
        allTerceros = terceroService.findAll();        
        return allTerceros;
    }

    public void setAllTerceros(List<TerceroEntity> allTerceros) {
        this.allTerceros = allTerceros;
    }

    public String persist() {

        String message;

        try {
            if(tercero.getId() == null){
                UsuarioEntity vendedor = usuarioService.findByLogin(                        
                    FacesContext.
                        getCurrentInstance().
                        getExternalContext().
                        getRemoteUser()
                );
                
                tercero.setUsuario(vendedor);
            }
            tercero = terceroService.save(tercero);
            message = "message_successfully_created";
            
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_optimistic_locking_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_save_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);

        return null;
    }

    public String delete() {

        String message;

        try {
            terceroService.remove(tercero);
            message = "message_successfully_deleted";
            reset();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));

        return null;
    }

    public void onDialogOpen(TerceroEntity tercero) {
        reset();
        this.tercero = tercero;
        
    }

    public void reset() {
        tercero = null;
        allTipoIdentificacionsList = null;

    }

    // Get a List of all tipoIdentificacion
    public List<TipoIdentificacionEntity> getTipoIdentificacions() {
        if (this.allTipoIdentificacionsList == null) {
            this.allTipoIdentificacionsList = tipoIdentificacionService.findAllTipoIdentificacionEntities();
        }
        return this.allTipoIdentificacionsList;
    }

    // Update tipoIdentificacion of the current tercero
    public void updateTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        this.tercero.setTipoIdentificacion(tipoIdentificacion);
        // Maybe we just created and assigned a new tipoIdentificacion. So reset the allTipoIdentificacionList.
        allTipoIdentificacionsList = null;
    }

    public TerceroEntity getTercero() {
        if (this.tercero == null) {
            prepareNewTercero();
        }
        return this.tercero;
    }

    public void setTercero(TerceroEntity tercero) {
        this.tercero = tercero;
    }    
    
    public void seleccionarPoblado(SelectEvent event) {
        this.tercero.setLugarExpedicion((PobladoEntity) event.getObject());
    }

}
