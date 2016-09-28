package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.service.DepartamentoService;
import com.j2km.inmueblesgo.service.MunicipioService;
import com.j2km.inmueblesgo.service.PobladoService;
import com.j2km.inmueblesgo.web.generic.GenericLazyDataModel;
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

@Named("pobladoBean")
@ViewScoped
public class PobladoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(PobladoBean.class.getName());

    private GenericLazyDataModel<PobladoEntity> lazyModel;

    private PobladoEntity poblado;

    @Inject
    private PobladoService pobladoService;

    @Inject
    private MunicipioService municipioService;

    private List<MunicipioEntity> allMunicipiosList;

    private DepartamentoEntity departamento;

    private List<DepartamentoEntity> allDepartamentosList;

    @Inject
    private DepartamentoService departamentoService;

    private DepartamentoEntity departamentoBusqueda;
    private MunicipioEntity municipioBusqueda;
    private PobladoEntity pobladoBusqueda;

    private List<PobladoEntity> allPobladosList;

    public void prepareNewPoblado() {
        reset();
        this.poblado = new PobladoEntity();
        // set any default values now, if you need
        // Example: this.poblado.setAnything("test");
    }

    public GenericLazyDataModel<PobladoEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(pobladoService);
        }
        return this.lazyModel;
    }

    public String persist() {

        String message;

        try {

            if (poblado.getId() != null) {
                poblado = pobladoService.update(poblado);
                message = "message_successfully_updated";
            } else {
                poblado = pobladoService.save(poblado);
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
            pobladoService.delete(poblado);
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

    public void onDialogOpen(PobladoEntity poblado) {
        reset();
        this.poblado = poblado;
        if (this.poblado.getMunicipio() != null) {
            System.out.println("Seleccionando municipio y departamento....");
            if (this.poblado.getMunicipio().getDepartamento() != null) {
                System.out.println("Seleccionando solo deprtamento....");
                this.departamento = this.poblado.getMunicipio().getDepartamento();
                this.allMunicipiosList = municipioService.findMunicipiosByDepartamento(this.departamento);
            }

        }

    }

    public void reset() {
        poblado = null;
        allMunicipiosList = null;

    }

    // Get a List of all municipio
    public List<MunicipioEntity> getMunicipios() {
        if (this.allMunicipiosList == null) {
            //this.allMunicipiosList = municipioService.findAllMunicipioEntities();
            if (this.allDepartamentosList != null || !this.allDepartamentosList.isEmpty()) {
                if (this.departamento == null) {
                    this.departamento = this.allDepartamentosList.get(0);
                }
                this.allMunicipiosList = municipioService.findMunicipiosByDepartamento(this.departamento);
            }
        }
        return this.allMunicipiosList;
    }

    // Update municipio of the current poblado
    public void updateMunicipio(MunicipioEntity municipio) {
        this.poblado.setMunicipio(municipio);
        // Maybe we just created and assigned a new municipio. So reset the allMunicipioList.
        allMunicipiosList = null;
    }

    public PobladoEntity getPoblado() {
        if (this.poblado == null) {
            prepareNewPoblado();
        }
        return this.poblado;
    }

    public void setPoblado(PobladoEntity poblado) {
        this.poblado = poblado;
    }

    public DepartamentoEntity getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoEntity departamento) {
        this.departamento = departamento;
    }

    public List<DepartamentoEntity> getDepartamentos() {
        if (this.allDepartamentosList == null) {
            this.allDepartamentosList = departamentoService.findAllDepartamentoEntities();
        }

        if (this.allDepartamentosList != null || !this.allDepartamentosList.isEmpty()) {
            if (this.departamento == null) {
                this.departamento = this.allDepartamentosList.get(0);
            }
            this.allMunicipiosList = municipioService.findMunicipiosByDepartamento(this.departamento);
        }

        return this.allDepartamentosList;

    }

    public void onCamiarDepartamento() {
        this.allMunicipiosList = municipioService.findMunicipiosByDepartamento(departamento);
    }

    public void onBuscarPoblado() {
        departamentoBusqueda = null;
        municipioBusqueda = null;
        pobladoBusqueda = null;

        this.allDepartamentosList = departamentoService.findAllDepartamentoEntities();
        if (this.allDepartamentosList != null || !this.allDepartamentosList.isEmpty()) {
            this.departamentoBusqueda = this.allDepartamentosList.get(0);
            if (this.departamentoBusqueda != null) {
                this.allMunicipiosList = municipioService.findMunicipiosByDepartamento(this.departamentoBusqueda);
                if (this.allMunicipiosList != null || !this.allMunicipiosList.isEmpty()) {
                    this.municipioBusqueda = allMunicipiosList.get(0);
                    if (this.municipioBusqueda != null) {
                        this.allPobladosList = pobladoService.findPobladosByMunicipio(this.municipioBusqueda);
                        if (this.allPobladosList != null || !this.allPobladosList.isEmpty()) {
                            this.pobladoBusqueda = allPobladosList.get(0);
                        }
                    }
                }
            }
        }

    }

    public void onCambiarDepartamentoBusqueda() {
        this.allMunicipiosList = municipioService.findMunicipiosByDepartamento(this.departamentoBusqueda);
        this.municipioBusqueda = null;
        this.allPobladosList = null;
        this.pobladoBusqueda = null;
    }

    public void onCambiarMunicipioBusqueda() {
        this.allPobladosList = pobladoService.findPobladosByMunicipio(this.municipioBusqueda);
        this.pobladoBusqueda = null;
    }

    public void resetBusqueda() {
        this.departamentoBusqueda = null;
        this.municipioBusqueda = null;
        this.pobladoBusqueda = null;
        this.allDepartamentosList = null;
        this.allMunicipiosList = null;
        this.allPobladosList = null;

    }

    public DepartamentoEntity getDepartamentoBusqueda() {
        return departamentoBusqueda;
    }

    public void setDepartamentoBusqueda(DepartamentoEntity departamentoBusqueda) {
        this.departamentoBusqueda = departamentoBusqueda;
    }

    public MunicipioEntity getMunicipioBusqueda() {
        return municipioBusqueda;
    }

    public void setMunicipioBusqueda(MunicipioEntity municipioBusqueda) {
        this.municipioBusqueda = municipioBusqueda;
    }

    public PobladoEntity getPobladoBusqueda() {
        return pobladoBusqueda;
    }

    public void setPobladoBusqueda(PobladoEntity pobladoBusqueda) {
        this.pobladoBusqueda = pobladoBusqueda;
    }

    public List<MunicipioEntity> getAllMunicipiosList() {
        return allMunicipiosList;
    }

    public void setAllMunicipiosList(List<MunicipioEntity> allMunicipiosList) {
        this.allMunicipiosList = allMunicipiosList;
    }

    public List<DepartamentoEntity> getAllDepartamentosList() {
        return allDepartamentosList;
    }

    public void setAllDepartamentosList(List<DepartamentoEntity> allDepartamentosList) {
        this.allDepartamentosList = allDepartamentosList;
    }

    public List<PobladoEntity> getAllPobladosList() {
        return allPobladosList;
    }

    public void setAllPobladosList(List<PobladoEntity> allPobladosList) {
        this.allPobladosList = allPobladosList;
    }

}
