package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.configuracion.Constantes;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import com.j2km.inmueblesgo.service.EstadoInmuebleRepository;
import com.j2km.inmueblesgo.service.EstadoProyectoRepository;
import com.j2km.inmueblesgo.service.InmuebleRepository;
import com.j2km.inmueblesgo.service.InmuebleService;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.NegociacionService;
import com.j2km.inmueblesgo.service.OfertaRepository;
import com.j2km.inmueblesgo.service.OfertaService;
import com.j2km.inmueblesgo.service.PlanPagoRepository;
import com.j2km.inmueblesgo.service.PlanPagoService;
import com.j2km.inmueblesgo.service.ProyectoRepository;
import com.j2km.inmueblesgo.service.TerceroRepository;
import com.j2km.inmueblesgo.service.TorreRepository;
import com.j2km.inmueblesgo.web.util.MessageFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import org.primefaces.event.SelectEvent;

@Named("negociacionBean")
@ViewScoped
public class NegociacionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(NegociacionBean.class.getName());

    private NegociacionEntity negociacion;

    @Inject private NegociacionRepository negociacionService;
    @Inject private TerceroRepository terceroService;
    @Inject private OfertaRepository ofertaService;
    @Inject private PlanPagoRepository planPagoService;
    @Inject private InmuebleRepository inmuebleService;
    @Inject private ProyectoRepository proyectoService;
    @Inject private EstadoProyectoRepository estadoProyectoService;
    @Inject private EstadoInmuebleRepository estadoInmuebleRepository;
    @Inject private TorreRepository torreRepository;

    private InmuebleBean inmuebleBean;

    private int cantidadCuotas;
    private Long proyectoId;
    private Long torreId;

    private List<TerceroEntity> allTerceroList;
    private List<OfertaEntity> allOfertaList;
    private List<PlanPagoEntity> allPlanPagosListNegociacion;
    private List<InmuebleEntity> inmueblesDisponiblesList;
    private List<ProyectoEntity> proyectoList;
    private List<TorreEntity> torreList;
    
    private InmuebleEntity inmuebleInstance;
    private TorreEntity torre;
    private ProyectoEntity proyecto;

    public TorreEntity getTorre() {
        return torre;
    }

    public void setTorre(TorreEntity torre) {
        this.torre = torre;
    }

    public ProyectoEntity getProyecto() {
        return proyecto;
    }

    public void setProyecto(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
    }

    public Long getTorreId() {
        return torreId;
    }

    public void setTorreId(Long torreId) {
        this.torreId = torreId;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public List<TorreEntity> getTorreList() {
        return torreList;
    }

    public void setTorreList(List<TorreEntity> torreList) {
        this.torreList = torreList;
    }   

    public List<InmuebleEntity> getInmueblesDisponiblesList() {
        return inmueblesDisponiblesList;
    }

    public void setInmueblesDisponiblesList(List<InmuebleEntity> inmueblesDisponiblesList) {
        this.inmueblesDisponiblesList = inmueblesDisponiblesList;
    }

    public List<PlanPagoEntity> getAllPlanPagosListNegociacion() {
        return allPlanPagosListNegociacion;
    }

    public void setAllPlanPagosListNegociacion(List<PlanPagoEntity> allPlanPagosListNegociacion) {
        this.allPlanPagosListNegociacion = allPlanPagosListNegociacion;
    }

    public List<ProyectoEntity> getProyectoList() {
        return proyectoList;
    }

    public void setProyectoList(List<ProyectoEntity> proyectoList) {
        this.proyectoList = proyectoList;
    }

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public List<TerceroEntity> getAllTerceroList() {
        return allTerceroList;
    }

    public void setAllTerceroList(List<TerceroEntity> allTerceroList) {
        this.allTerceroList = allTerceroList;
    }

    public List<OfertaEntity> getAllOfertaList() {
        return allOfertaList;
    }

    public void setAllOfertaList(List<OfertaEntity> allOfertaList) {
        this.allOfertaList = allOfertaList;
    }

    public void prepareNewNegociacion() {
       
        this.negociacion = new NegociacionEntity();
        Calendar cal = Calendar.getInstance();
        this.negociacion.setFecha(cal.getTime());

        this.allTerceroList = terceroService.findAll();
        this.allOfertaList = ofertaService.findAll();
        this.cantidadCuotas = 0;
        this.allPlanPagosListNegociacion = null;
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }
    
    public void inicio(){
        this.proyectoList = proyectoService.findByEstadoProyecto(
                estadoProyectoService.findOptionalByNombre(Constantes.PROYECTO_ACTIVO));
    }

    public void nuevaNegociacion(Long inmuebleId) {

        System.out.println("Seleccionado inmueble...." + inmuebleId);

        InmuebleEntity inmuebleInstance = inmuebleService.findBy(inmuebleId);
        System.out.println(inmuebleInstance);
        
        this.negociacion = negociacionService.findOptionalByInmueble(inmuebleInstance);
    
        if (this.negociacion == null){
            this.negociacion = new NegociacionEntity();
            Calendar cal = Calendar.getInstance();
            this.negociacion.setFecha(cal.getTime());
            this.negociacion.setValorMetroCuadrado(inmuebleInstance.getValorMetroCuadrado());
            this.negociacion.setValorSeparacion(inmuebleInstance.getValorSeparacion());
            this.negociacion.setValorIncremento(inmuebleInstance.getIncremento());
            
            this.cantidadCuotas = 0;
            this.allPlanPagosListNegociacion = null;
        }else{
            this.allPlanPagosListNegociacion = planPagoService.findByNegociacion(this.negociacion);
            this.cantidadCuotas = allPlanPagosListNegociacion.size();
        }
        
        
        this.allTerceroList = terceroService.findAll();
        this.allOfertaList = ofertaService.findAll();
    }

    public String persist() {

        String message;

        try {

            if (negociacion.getId() != null) {
                negociacion = negociacionService.save(negociacion);
                message = "message_successfully_updated";
            } else {
                negociacion = negociacionService.save(negociacion);
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
            negociacionService.remove(negociacion);
            message = "message_successfully_deleted";
       
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));

        return null;
    }

    public NegociacionEntity getNegociacion() {
        if (this.negociacion == null) {
            prepareNewNegociacion();
        }
        return this.negociacion;
    }

    public void setNegociacion(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
    }
    
    public void inicioTorres(){        
        proyecto = proyectoService.findBy(proyectoId);
        torreList = torreRepository.findByProyecto(proyecto);
    }
    
    public void inicioInmuebles(){        
        torre = torreRepository.findBy(torreId);
        inmueblesDisponiblesList = inmuebleService.findByTorre(torre);
    }
    
    public String seleccionarProyecto(ProyectoEntity proyecto){
        return "/pages/vendedor/torres?faces-redirect=true&amp;id=".concat(proyecto.getId().toString());       
    }
    
    public String seleccionarTorre(TorreEntity torre){
        return "/pages/vendedor/inmuebles?faces-redirect=true&amp;id=".concat(torre.getId().toString());       
    }
  
    
}
