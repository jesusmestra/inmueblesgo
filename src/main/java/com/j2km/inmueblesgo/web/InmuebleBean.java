package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.service.EstadoInmuebleRepository;
import com.j2km.inmueblesgo.service.InmuebleRepository;
import com.j2km.inmueblesgo.service.InmuebleService;
import com.j2km.inmueblesgo.service.ProyectoRepository;
import com.j2km.inmueblesgo.service.ProyectoService;
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

@Named("inmuebleBean")
@ViewScoped
public class InmuebleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(InmuebleBean.class.getName());

    private InmuebleEntity inmueble;
    private Long inmuebleId;

    @Inject
    private InmuebleRepository inmuebleService;

    @Inject
    private ProyectoRepository proyectoService;

    private List<ProyectoEntity> allProyectosList;

    @Inject
    private EstadoInmuebleRepository estadoInmuebleService;

    private List<EstadoInmuebleEntity> allEstadoInmueblesList;

    private int cantidadInmuebles;

    public int getCantidadInmuebles() {
        return cantidadInmuebles;
    }

    public void setCantidadInmuebles(int cantidadInmuebles) {
        this.cantidadInmuebles = cantidadInmuebles;
    }

    public void prepareNewInmueble() {
        reset();
        this.inmueble = new InmuebleEntity();
        // set any default values now, if you need
        // Example: this.inmueble.setAnything("test");
    }

    public String persist() {
        System.out.println("Creanco persistencia.....");
        String message;
        String mensaje = "";

        try {

            if (inmueble.getId() != null) {
                inmueble = inmuebleService.save(inmueble);
                message = "message_successfully_updated";
                mensaje = "Inmueble modificado con éxito";
            } else {
                inmueble = inmuebleService.save(inmueble);
                message = "message_successfully_created";
                mensaje = "Inmueble creado con éxito";
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
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));

        return null;
    }

    public String delete() {

        String message;

        try {
            inmuebleService.remove(inmueble);
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

    public void onDialogOpen(InmuebleEntity inmueble) {
        reset();
        this.inmueble = inmueble;
    }

    public void reset() {
        inmueble = null;

        allProyectosList = null;
        allEstadoInmueblesList = null;

    }

    // Get a List of all proyecto
    public List<ProyectoEntity> getProyectos() {
        if (this.allProyectosList == null) {
            this.allProyectosList = proyectoService.findAll();
        }
        return this.allProyectosList;
    }

    // Update proyecto of the current inmueble
    public void updateProyecto(ProyectoEntity proyecto) {
        this.inmueble.setProyecto(proyecto);
        // Maybe we just created and assigned a new proyecto. So reset the allProyectoList.
        allProyectosList = null;
    }

    // Get a List of all proyecto
    public List<EstadoInmuebleEntity> getEstadoInmuebles() {
        if (this.allEstadoInmueblesList == null) {
            this.allEstadoInmueblesList = estadoInmuebleService.findAll();
        }
        return this.allEstadoInmueblesList;
    }

    // Update estadoInmueble of the current inmueble
    public void updateestadoInmueble(EstadoInmuebleEntity estadoInmueble) {
        this.inmueble.setEstadoInmueble(estadoInmueble);
        // Maybe we just created and assigned a new estadoInmueble. So reset the allEstadoInmuebleList.
        allEstadoInmueblesList = null;
    }

    public InmuebleEntity getInmueble() {
        if (this.inmueble == null) {
            prepareNewInmueble();
        }
        return this.inmueble;
    }

    public void setInmueble(InmuebleEntity inmueble) {
        this.inmueble = inmueble;
    }

    public void onDialogGenerarInmuebles() {
        System.out.println("Iniciando la generacion masiva.....");
        this.cantidadInmuebles = 0;
    }

    public String generacionMasiva() {
        System.out.println("Inicio de la Generacion masiva");
        String message;

        try {
            // PROCEDEMOS A CREAR UNA MASIVA DE INMUEBLES    
            
            
            
            System.out.println("Generacion masiva creada");
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
    
    public double valorTotal() {
        
        double tempo = inmueble.getValorMetroCuadrado() * inmueble.getArea();
        
        if (inmueble.getIncremento() != null ){
            tempo += inmueble.getIncremento();
        }
        //inmueble.setValorTotal(tempo);
        
        return 0;
    }

    public Long getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(Long inmuebleId) {
        this.inmuebleId = inmuebleId;
    }
    
    public void inicioVistaInmueble() {
        this.inmueble = inmuebleService.findBy(inmuebleId);
    }

}
