package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.Piso;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import com.j2km.inmueblesgo.service.ConfiguracionService;
import com.j2km.inmueblesgo.service.EmpresaService;
import com.j2km.inmueblesgo.service.EstadoInmuebleService;
import com.j2km.inmueblesgo.service.EstadoProyectoService;
import com.j2km.inmueblesgo.service.InmuebleService;
import com.j2km.inmueblesgo.service.NegociacionService;
import com.j2km.inmueblesgo.service.OfertaService;
import com.j2km.inmueblesgo.service.PisoService;
import com.j2km.inmueblesgo.service.PobladoService;
import com.j2km.inmueblesgo.service.ProyectoService;
import com.j2km.inmueblesgo.service.TorreService;
import com.j2km.inmueblesgo.web.generic.GenericLazyDataModel;
import com.j2km.inmueblesgo.web.util.MessageFactory;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named("proyectoBean")
@ViewScoped
public class ProyectoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ProyectoBean.class.getName());

    private GenericLazyDataModel<ProyectoEntity> lazyModel;

    private ProyectoEntity proyecto;

    @Inject
    private ProyectoService proyectoService;

    @Inject
    private OfertaService ofertaService;

    private List<OfertaEntity> allOfertasList;

    @Inject
    private PobladoService pobladoService;

    private List<PobladoEntity> allPobladosList;

    @Inject
    private EmpresaService empresaService;

    private List<EmpresaEntity> allEmpresasList;

    @Inject
    private EstadoProyectoService estadoProyectoService;

    private List<EstadoProyectoEntity> estadoProyectoList;

    private EstadoProyectoEntity estadoProyectoSel;

    private List<ProyectoEntity> proyectoEntityList;

    @Inject
    private EstadoInmuebleService estadoInmuebleService;

    private List<EstadoInmuebleEntity> estadoInmuebleList;

    private EstadoInmuebleEntity estadoInmuebleSel;

    private List<InmuebleEntity> inmuebleEntityList;

    @Inject
    private InmuebleService inmuebleService;

    @Inject
    private NegociacionService negociacionService;

    private UploadedFile archivoLogo;

    @Inject
    private ConfiguracionService configuracionServiceInstance;

    public void prepareNewProyecto() {
        reset();
        this.proyecto = new ProyectoEntity();
        
        this.estadoProyectoList = estadoProyectoService.findAllEstadoProyectoEntities();
        // set any default values now, if you need
        // Example: this.proyecto.setAnything("test");
    }

    public GenericLazyDataModel<ProyectoEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(proyectoService);
        }
        return this.lazyModel;
    }

    public String persist() throws IOException {

        String message;
        String nombreLogo;

        try {

            if (proyecto.getId() != null) {
                proyecto = proyectoService.update(proyecto);
                message = "message_successfully_updated";
            } else {
                proyecto = proyectoService.save(proyecto);
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
        } finally {
            if (proyecto.getId() != null) {

                if (archivoLogo != null) {
                    nombreLogo = configuracionServiceInstance.copiarArchivo(archivoLogo, "proyecto_" + proyecto.getId().toString(), "proyecto");
                    if (nombreLogo != null && nombreLogo.length() > 0) {
                        proyecto.setLogo(nombreLogo);
                    }

                }
                if (proyecto.getLogo() != null && proyecto.getLogo().length() > 0) {
                    proyecto = proyectoService.update(proyecto);
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
            proyectoService.delete(proyecto);
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

    public void onDialogOpen(ProyectoEntity proyecto) {
        reset();
        this.proyecto = proyecto;
        this.estadoProyectoList = estadoProyectoService.findAllEstadoProyectoEntities();
    }

    public void reset() {
        proyecto = null;

        allOfertasList = null;
        allEmpresasList = null;
        allPobladosList = null;
    }

    // Get a List of all oferta
    public List<OfertaEntity> getOfertas() {
        if (this.allOfertasList == null) {
            this.allOfertasList = ofertaService.findAllOfertaEntities();
        }
        return this.allOfertasList;
    }

    // Update oferta of the current proyecto
    public void updateOferta(OfertaEntity oferta) {
        this.proyecto.setOferta(oferta);
        // Maybe we just created and assigned a new oferta. So reset the allOfertaList.
        allOfertasList = null;
    }

    // Get a List of all oferta
    public List<EmpresaEntity> getEmpresas() {
        if (this.allEmpresasList == null) {
            this.allEmpresasList = empresaService.findAllEmpresaEntities();
        }
        return this.allEmpresasList;
    }

    // Update empresa of the current proyecto
    public void updateempresa(EmpresaEntity empresa) {
        this.proyecto.setEmpresa(empresa);
        // Maybe we just created and assigned a new empresa. So reset the allEmpresaList.
        allEmpresasList = null;
    }

    // Get a List of all oferta
    public List<PobladoEntity> getPoblados() {
        if (this.allPobladosList == null) {
            this.allPobladosList = pobladoService.findAllPobladoEntities();
        }
        return this.allPobladosList;
    }

    // Update oferta of the current proyecto
    public void updatePoblado(PobladoEntity poblado) {
        this.proyecto.setPoblado(poblado);
        // Maybe we just created and assigned a new oferta. So reset the allOfertaList.
        allPobladosList = null;
    }

    public ProyectoEntity getProyecto() {
        if (this.proyecto == null) {
            prepareNewProyecto();
        }
        return this.proyecto;
    }

    public void setProyecto(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * ****************
     */
    public void inicioListaProyecto() {
        this.estadoProyectoSel = estadoProyectoService.findByCodigo("01");
        this.estadoProyectoList = estadoProyectoService.findAllEstadoProyectoEntities();
        if (this.estadoProyectoSel == null) {
            this.proyectoEntityList = proyectoService.findAllProyectoEntities();
        } else {
            this.proyectoEntityList = proyectoService.findAllByEstado(estadoProyectoSel);
        }

    }

    public void inicioListaInmueble() {
        this.estadoInmuebleSel = estadoInmuebleService.findByCodigo("01");
        this.estadoInmuebleList = estadoInmuebleService.findAllEstadoInmuebleEntities();
        if (this.estadoInmuebleSel == null) {
            inmuebleService.findInmueblesByProyecto(proyecto);
        } else {
            this.inmuebleEntityList = inmuebleService.findAllInmueblesByProyectoAndEstado(proyecto, estadoInmuebleSel);
        }

    }

    public List<EstadoInmuebleEntity> getEstadoInmuebleList() {
        return estadoInmuebleList;
    }

    public void setEstadoInmuebleList(List<EstadoInmuebleEntity> estadoInmuebleList) {
        this.estadoInmuebleList = estadoInmuebleList;
    }

    public EstadoInmuebleEntity getEstadoInmuebleSel() {
        return estadoInmuebleSel;
    }

    public void setEstadoInmuebleSel(EstadoInmuebleEntity estadoInmuebleSel) {
        this.estadoInmuebleSel = estadoInmuebleSel;
    }

    public List<InmuebleEntity> getInmuebleEntityList() {
        return inmuebleEntityList;
    }

    public void setInmuebleEntityList(List<InmuebleEntity> inmuebleEntityList) {
        this.inmuebleEntityList = inmuebleEntityList;
    }

    public List<EstadoProyectoEntity> getEstadoProyectoList() {
        return estadoProyectoList;
    }

    public void setEstadoProyectoList(List<EstadoProyectoEntity> estadoProyectoList) {
        this.estadoProyectoList = estadoProyectoList;
    }

    public EstadoProyectoEntity getEstadoProyectoSel() {
        return estadoProyectoSel;
    }

    public void setEstadoProyectoSel(EstadoProyectoEntity estadoProyectoSel) {
        this.estadoProyectoSel = estadoProyectoSel;
    }

    public List<ProyectoEntity> getProyectoEntityList() {
        return proyectoEntityList;
    }

    public void setProyectoEntityList(List<ProyectoEntity> proyectoEntityList) {
        this.proyectoEntityList = proyectoEntityList;
    }

    public void cambioEstadoProyecto() {

        if (this.estadoProyectoSel == null) {
            this.proyectoEntityList = proyectoService.findAllProyectoEntities();
        } else {
            this.proyectoEntityList = proyectoService.findAllByEstado(estadoProyectoSel);
        }

    }

    public void cambioEstadoInmueble() {

        System.err.println("Estado Inmueble" + this.estadoInmuebleSel);

        if (this.estadoInmuebleSel == null) {
            this.inmuebleEntityList = inmuebleService.findInmueblesByProyecto(proyecto);
        } else {
            this.inmuebleEntityList = inmuebleService.findAllInmueblesByProyectoAndEstado(proyecto, estadoInmuebleSel);
        }

    }

    public String redireccionaNegociacion(InmuebleEntity inmueble) {
        String ruta = "";
        if (inmueble.getEstadoInmueble().getId() == 1) {
            ruta = "/pages/vendedor/negociacion?faces-redirect=true&amp;id=".concat(inmueble.getId().toString());
        } else {
            NegociacionEntity n = negociacionService.findByInmueble(inmueble);
            if (n != null) {
                ruta = "/pages/vendedor/negociacionView?faces-redirect=true&amp;id=".concat(n.getId().toString());
            }
        }
        return ruta;
    }

    public void seleccionarPoblado(SelectEvent event) {
        this.proyecto.setPoblado((PobladoEntity) event.getObject());
    }

    public UploadedFile getArchivoLogo() {
        return archivoLogo;
    }

    public void setArchivoLogo(UploadedFile archivoLogo) {
        this.archivoLogo = archivoLogo;
    }
    
    /* PARA CREAR LAS TORRES Y LOS INMUEBLES */
     
      @Inject
    private TorreService torreService;
        @Inject
    private PisoService pisoService;
    private ProyectoEntity proyectoTorre;
    private TorreEntity torreNueva;
    private TorreEntity torreInstance;
      private List <TorreEntity> torreListProyecto;
 
    public TorreEntity getTorreInstance() {
        return torreInstance;
    }

    public void setTorreInstance(TorreEntity torreInstance) {
        this.torreInstance = torreInstance;
    }

    public TorreEntity getTorreNueva() {
        return torreNueva;
    }

    public void setTorreNueva(TorreEntity torreNueva) {
        this.torreNueva = torreNueva;
    }

    public ProyectoEntity getProyectoTorre() {
        return proyectoTorre;
    }

    public void setProyectoTorre(ProyectoEntity proyectoTorre) {
        this.proyectoTorre = proyectoTorre;
    }
    
    
    
    public void dialogoTorreProyecto() {

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", "80%");
        options.put("height", 400);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        List<String> paramList = new ArrayList<String>();
        paramList.add(this.proyecto.getId().toString());
        Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
        paramMap.put("id", paramList);
        
        RequestContext.getCurrentInstance().openDialog("/pages/proyecto/crearTorreProyectoInclude.", options, paramMap);
        
    } 
    
  

    public List<TorreEntity> getTorreListProyecto() {
        return torreListProyecto;
    }

    public void setTorreListProyecto(List<TorreEntity> torreListProyecto) {
        this.torreListProyecto = torreListProyecto;
    }
  
    
    public void resetTorreProyecto() {
        this.torreNueva = new TorreEntity();
        this.torreNueva.setProyecto(this.proyectoTorre);
        this.torreListProyecto = torreService.findAllByProyecto(this.proyectoTorre);
    }
    
    
    
     public void dialogoPisosTorre(TorreEntity t) {

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", "80%");
        options.put("height", 400);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        //options.put("headerElement", "customheader");
        List<String> paramList = new ArrayList<String>();
         
        paramList.add(t.getId().toString());
        Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
        paramMap.put("id", paramList);
        
        //RequestContext.getCurrentInstance().openDialog("/pages/proyecto/pisoTorreInclude.", options, paramMap);
        RequestContext.getCurrentInstance().openDialog("/pages/torre/torreViewTemplate.", options, paramMap);
        
    } 
    
    public void grabarTorre(){
        torreService.save(torreNueva);
        resetTorreProyecto();
    }
    
    /* PARA CREAR LAS TORRES Y LOS INMUEBLES */

}
