package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.PlanPagoRepository;
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

@Named("planPagoBean")
@ViewScoped
public class PlanPagoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(PlanPagoBean.class.getName());
    
    private PlanPagoEntity planPago;
    
    @Inject
    private PlanPagoRepository planPagoService;
    
    @Inject
    private NegociacionRepository negociacionService;
    
    private List<NegociacionEntity> allNegociacionsList;
    
    public void prepareNewPlanPago() {
        reset();
        this.planPago = new PlanPagoEntity();
        // set any default values now, if you need
        // Example: this.planPago.setAnything("test");
    }
    
    public String persist() {

        String message;
        
        try {
            
            if (planPago.getId() != null) {
                planPago = planPagoService.save(planPago);
                message = "message_successfully_updated";
            } else {
                planPago = planPagoService.save(planPago);
                message = "message_successfully_created";
            }
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
            planPagoService.remove(planPago);
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
    
    public void onDialogOpen(PlanPagoEntity planPago) {
        reset();
        this.planPago = planPago;
    }
    
    public void reset() {
        planPago = null;

        allNegociacionsList = null;
        
    }

    // Get a List of all departamento
    public List<NegociacionEntity> getDepartamentos() {
        if (this.allNegociacionsList == null) {
            this.allNegociacionsList = negociacionService.findAll();
        }
        return this.allNegociacionsList;
    }
    
    // Update departamento of the current planPago
    public void updateNegociacion(NegociacionEntity negociacion) {
        this.planPago.setNegociacion(negociacion);
        // Maybe we just created and assigned a new departamento. So reset the allDepartamentoList.
        allNegociacionsList = null;
    }
    
    public PlanPagoEntity getPlanPago() {
        if (this.planPago == null) {
            prepareNewPlanPago();
        }
        return this.planPago;
    }
    
    public void setPlanPago(PlanPagoEntity planPago) {
        this.planPago = planPago;
    }
    
}
