package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.service.TerceroService;
import com.j2km.inmueblesgo.service.TipoIdentificacionService;
import com.j2km.inmueblesgo.web.generic.GenericLazyDataModel;
import com.j2km.inmueblesgo.web.util.MessageFactory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("terceroBean")
@ViewScoped
public class TerceroBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TerceroBean.class.getName());

    private GenericLazyDataModel<TerceroEntity> lazyModel;

    private TerceroEntity tercero;

    @Inject
    private TerceroService terceroService;

    @Inject
    private TipoIdentificacionService tipoIdentificacionService;

    private List<TipoIdentificacionEntity> allTipoIdentificacionsList;

    public void prepareNewTercero() {
        reset();
        this.tercero = new TerceroEntity();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }

    public GenericLazyDataModel<TerceroEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(terceroService);
        }
        return this.lazyModel;
    }

    public String persist() {

        String message;

        try {

            if (tercero.getId() != null) {
                tercero = terceroService.update(tercero);
                message = "message_successfully_updated";
            } else {
                tercero = terceroService.save(tercero);
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
            terceroService.delete(tercero);
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

    private TerceroEntity terceroBuscar;
    private List<TerceroEntity> allTercerosBuscar;

    public List<TerceroEntity> getAllTercerosBuscar() {
        return allTercerosBuscar;
    }

    public void setAllTercerosBuscar(List<TerceroEntity> allTercerosBuscar) {
        this.allTercerosBuscar = allTercerosBuscar;
    }

    public TerceroEntity getTerceroBuscar() {
        return terceroBuscar;
    }

    public void setTerceroBuscar(TerceroEntity terceroBuscar) {
        this.terceroBuscar = terceroBuscar;
    }

    public void onBuscarTercero() {
        resetBusqueda();
        terceroBuscar = new TerceroEntity();

    }

    public void resetBusqueda() {
        this.terceroBuscar = null;
        this.allTercerosBuscar = null;
    }

    public void buscarTercero() {
        System.out.println("Enter presionado");
        this.allTercerosBuscar = terceroService.buscarTercerosFiltro(terceroBuscar);
    }

    public void seleccionar(SelectEvent event) {
        System.err.println("Llegando a selccionar");
        TerceroEntity terceroS = (TerceroEntity) event.getObject();
        System.out.println("Tercero seleccionado....." + terceroS);
    }

    public void selectCarFromDialog(TerceroEntity tercero) {
        System.err.println("Cerrando dialogo");
        RequestContext.getCurrentInstance().closeDialog(tercero);
    }

    public void resetBuscar() {
        terceroBuscar = new TerceroEntity();
    }

    public void chooseTercero() {

        System.out.println("Selecionando tercero");
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", "80%");
        options.put("height", 400);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().openDialog("/pages/tercero/terceroBuscarInclude", options, null);
        System.out.println("Terminado selec");
    }

}
