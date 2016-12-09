package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.service.ConfiguracionService;
import com.j2km.inmueblesgo.service.EmpresaService;
import com.j2km.inmueblesgo.service.PobladoService;
import com.j2km.inmueblesgo.service.TerceroService;
import com.j2km.inmueblesgo.web.generic.GenericLazyDataModel;
import com.j2km.inmueblesgo.web.util.MessageFactory;
import java.io.IOException;

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
import org.primefaces.model.UploadedFile;

@Named("empresaBean")
@ViewScoped
public class EmpresaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(EmpresaBean.class.getName());
    
    private GenericLazyDataModel<EmpresaEntity> lazyModel;
    
    private EmpresaEntity empresa;
    
    @Inject
    private EmpresaService empresaService;
    
    @Inject
    private PobladoService pobladoService;
    
    private List<PobladoEntity> allPobladosList;

    @Inject
    private TerceroService representanteService;
    
    private List<TerceroEntity> allRepresentantesList;
    
    private MunicipioEntity municipioBusqueda;

    private UploadedFile archivoLogo;

    public UploadedFile getArchivoLogo() {
        return archivoLogo;
    }

    public void setArchivoLogo(UploadedFile archivoLogo) {
        this.archivoLogo = archivoLogo;
    }
    
    @Inject
    private ConfiguracionService configuracionServiceInstance;
    
    public void prepareNewEmpresa() {
        reset();
        this.empresa = new EmpresaEntity();
        
        // set any default values now, if you need
        // Example: this.empresa.setAnything("test");
    }

    public GenericLazyDataModel<EmpresaEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(empresaService);
        }
        return this.lazyModel;
    }
    
    public String persist() throws IOException {

        String message;
         String nombreLogo;
        try {
            
            if (empresa.getId() != null) {
                empresa = empresaService.update(empresa);
                message = "message_successfully_updated";
            } else {
                empresa = empresaService.save(empresa);
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
        }finally {
            System.err.println("Iniciando la carga del archivo");
            if (empresa.getId() != null) {
                
                System.err.println("Copiando el del archivo"+archivoLogo);
                
                if (archivoLogo != null) {
                    nombreLogo = configuracionServiceInstance.copiarArchivo(archivoLogo, "empresa_" + empresa.getId().toString(), "empresa");
                    if (nombreLogo != null && nombreLogo.length() > 0) {
                        empresa.setLogo(nombreLogo);
                    }

                }
                if (empresa.getLogo() != null && empresa.getLogo().length() > 0) {
                    empresa = empresaService.update(empresa);
                }
            }

        }
        
        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            empresaService.delete(empresa);
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
    
    public void onDialogOpen(EmpresaEntity empresa) {
        reset();
        this.empresa = empresa;
        if(this.empresa.getPoblado()!=null){
            this.allPobladosList = pobladoService.findPobladosByMunicipio(this.empresa.getPoblado().getMunicipio());
            this.municipioBusqueda = this.empresa.getPoblado().getMunicipio();
        }
    }
    
    public void reset() {
        empresa = null;

        allPobladosList = null;
        allRepresentantesList = null;
        
    }

    // Get a List of all poblado
    public List<PobladoEntity> getPoblados() {
        /*if (this.allPobladosList == null) {
            this.allPobladosList = pobladoService.findAllPobladoEntities();
        }*/
        return this.allPobladosList;
    }
    
    // Update poblado of the current empresa
    public void updatePoblado(PobladoEntity poblado) {
        this.empresa.setPoblado(poblado);
        // Maybe we just created and assigned a new poblado. So reset the allPobladoList.
        allPobladosList = null;
    }
    
    
    // Get a List of all terceros
    public List<TerceroEntity> getRepresentantes() {
        if (this.allRepresentantesList == null) {
            this.allRepresentantesList = representanteService.findAllTerceroEntities();
        }
        return this.allRepresentantesList;
    }
    
    // Update terceros of the current empresa
    public void updateRepresentante(TerceroEntity representante) {
        this.empresa.setRepresentante(representante);
        // Maybe we just created and assigned a new poblado. So reset the allReprentanteList.
        allRepresentantesList = null;
    }
    
    
    public EmpresaEntity getEmpresa() {
        if (this.empresa == null) {
            prepareNewEmpresa();
        }
        return this.empresa;
    }
    
    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }
    
        // Update poblado of the current empresa
    public void updateListaPoblado(List<PobladoEntity> listaPoblado, MunicipioEntity municipio) {
        this.allPobladosList = listaPoblado;
        this.municipioBusqueda = municipio;
    }

    public List<PobladoEntity> getAllPobladosList() {
        return allPobladosList;
    }

    public void setAllPobladosList(List<PobladoEntity> allPobladosList) {
        this.allPobladosList = allPobladosList;
    }

    public MunicipioEntity getMunicipioBusqueda() {
        return municipioBusqueda;
    }

    public void setMunicipioBusqueda(MunicipioEntity municipioBusqueda) {
        this.municipioBusqueda = municipioBusqueda;
    }
    
    public void updateSelectRepresentante(TerceroEntity tercero) {
        this.empresa.setRepresentante(tercero);
    }
    
    public void seleccionar(SelectEvent event) {
        this.empresa.setRepresentante((TerceroEntity) event.getObject());
    }
    
    public void seleccionarPoblado(SelectEvent event) {
        this.empresa.setPoblado((PobladoEntity) event.getObject());
    }

    
    
}
