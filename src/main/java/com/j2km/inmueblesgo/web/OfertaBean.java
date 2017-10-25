package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.service.OfertaRepository;
import com.j2km.inmueblesgo.service.OfertaService;
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

@Named("ofertaBean")
@ViewScoped
public class OfertaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(OfertaBean.class.getName());    
    private OfertaEntity oferta;
    private List<OfertaEntity> ofertaList;
    
    @Inject
    private OfertaRepository ofertaService;
    

    public void persist() {

        String message;
        
        try {            
            oferta = ofertaService.save(oferta);            
            reset();
             FacesContext.getCurrentInstance().addMessage(
                null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Oferta guardada con éxito", "Oferta guardada con éxito"));
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);           
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);          
        }

    }
    
    public String delete() {
        
        String message;
        
        try {
            ofertaService.remove(oferta);
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
    
    public void reset() {
        this.oferta = new OfertaEntity();
        ofertaList = ofertaService.findAll();
        //allTipoIdentificacionsList = null;
        
    }

    public List<OfertaEntity> getOfertaList() {
        return ofertaList;
    }

    public void setOfertaList(List<OfertaEntity> ofertaList) {
        this.ofertaList = ofertaList;
    }    
    
    public OfertaEntity getOferta() {        
        return this.oferta;
    }
    
    public void setOferta(OfertaEntity oferta) {
        this.oferta = oferta;
    }
    
    public void seleccionar(OfertaEntity oferta){
        this.oferta = oferta;
    }
    
}
