package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.configuracion.Constantes;
import com.j2km.inmueblesgo.domain.Archivo;
import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.Kit;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.PisoEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.TipoInmuebleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaDetalleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import com.j2km.inmueblesgo.service.ArchivoRepository;
import com.j2km.inmueblesgo.service.ArchivoService;
import com.j2km.inmueblesgo.service.ConfiguracionService;
import com.j2km.inmueblesgo.service.DepartamentoRepository;
import com.j2km.inmueblesgo.service.EmpresaRepository;
import com.j2km.inmueblesgo.service.EstadoInmuebleRepository;
import com.j2km.inmueblesgo.service.EstadoProyectoRepository;
import com.j2km.inmueblesgo.service.InmuebleRepository;
import com.j2km.inmueblesgo.service.KitRepository;
import com.j2km.inmueblesgo.service.MunicipioRepository;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.OfertaRepository;
import com.j2km.inmueblesgo.service.PisoRepository;
import com.j2km.inmueblesgo.service.PobladoRepository;
import com.j2km.inmueblesgo.service.ProyectoRepository;
import com.j2km.inmueblesgo.service.TipoInmuebleRepository;
import com.j2km.inmueblesgo.service.TipoPlantaDetalleRepository;
import com.j2km.inmueblesgo.service.TipoPlantaRepository;
import com.j2km.inmueblesgo.service.TorreRepository;
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
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named("proyectoBean")
@ViewScoped
public class ProyectoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ProyectoBean.class.getName());

    private ProyectoEntity proyecto;
    private Long proyectoId;
    private TipoInmuebleEntity tipoInmueble;
    private TipoPlantaEntity tipoPlanta;
    private TipoPlantaEntity tipoPlantaSeleccionado;
    private TorreEntity torreSeleccionada;
    private PisoEntity pisoSeleccionado;
    private InmuebleEntity inmuebleSeleccionado;
    private Kit kit;

    private List<TipoPlantaDetalleEntity> tipoPlantaDetalleList;
    private List<TipoInmuebleEntity> tipoInmuebleList;
    private List<PisoEntity> pisoList;
    private List<Kit> kitList;

    private List<OfertaEntity> allOfertasList;
    private List<EmpresaEntity> allEmpresasList;
    private List<EstadoProyectoEntity> estadoProyectoList;
    private EstadoProyectoEntity estadoProyectoSel;
    private List<ProyectoEntity> proyectoEntityList;
    private List<EstadoInmuebleEntity> estadoInmuebleList;
    private EstadoInmuebleEntity estadoInmuebleSel;
    private List<InmuebleEntity> inmuebleEntityList;

    private List<PobladoEntity> allPobladosList;
    private List<MunicipioEntity> municipioList;
    private List<DepartamentoEntity> departamentoList;
    private EmpresaEntity empresaSeleccionada;

    //para los select del poblado
    private DepartamentoEntity departamento;
    private MunicipioEntity municipio;

    private Archivo archivo;

    private Archivo archivoTipoInmueble;
    private UploadedFile uploadedTipoInmueble;
    private String imagenTipoInmueble;

    private Archivo archivoTipoPlanta;
    private String imagenTipoPlanta;
    private UploadedFile uploadedTipoPlanta;

    @Inject
    private KitRepository kitService;
    @Inject
    private ApplicationBean applicationBean;
    @Inject
    private ProyectoRepository proyectoService;
    @Inject
    private OfertaRepository ofertaService;
    @Inject
    private TorreRepository torreService;
    @Inject
    private PisoRepository pisoService;
    @Inject
    private DepartamentoRepository departamentoService;
    @Inject
    private MunicipioRepository municipioService;
    @Inject
    private PobladoRepository pobladoService;
    @Inject
    private EmpresaRepository empresaService;
    @Inject
    private EstadoProyectoRepository estadoProyectoService;
    @Inject
    private EstadoInmuebleRepository estadoInmuebleService;
    @Inject
    private InmuebleRepository inmuebleService;
    @Inject
    private NegociacionRepository negociacionService;
    @Inject
    private ConfiguracionService configuracionServiceInstance;
    @Inject
    private TipoInmuebleRepository tipoInmuebleService;
    @Inject
    private TipoPlantaRepository tipoPlantaService;
    @Inject
    private TipoPlantaDetalleRepository tipoPlantaDetalleService;
    @Inject
    private ArchivoRepository archivoRepository;

    @Inject
    private ArchivoService archivoService;

    public Archivo getArchivoTipoPlanta() {
        return archivoTipoPlanta;
    }

    public void setArchivoTipoPlanta(Archivo archivoTipoPlanta) {
        this.archivoTipoPlanta = archivoTipoPlanta;
    }

    public String getImagenTipoPlanta() {
        return imagenTipoPlanta;
    }

    public void setImagenTipoPlanta(String imagenTipoPlanta) {
        this.imagenTipoPlanta = imagenTipoPlanta;
    }

    public String getImagenTipoInmueble() {
        return imagenTipoInmueble;
    }

    public void setImagenTipoInmueble(String imagenTipoInmueble) {
        this.imagenTipoInmueble = imagenTipoInmueble;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public InmuebleEntity getInmuebleSeleccionado() {
        return inmuebleSeleccionado;
    }

    public void setInmuebleSeleccionado(InmuebleEntity inmuebleSeleccionado) {
        this.inmuebleSeleccionado = inmuebleSeleccionado;
    }

    public EmpresaEntity getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(EmpresaEntity empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public DepartamentoEntity getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoEntity departamento) {
        this.departamento = departamento;
    }

    public MunicipioEntity getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioEntity municipio) {
        this.municipio = municipio;
    }

    public TipoInmuebleEntity getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmuebleEntity tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public TipoPlantaEntity getTipoPlanta() {
        return tipoPlanta;
    }

    public void setTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }

    public TipoPlantaEntity getTipoPlantaSeleccionado() {
        return tipoPlantaSeleccionado;
    }

    public void setTipoPlantaSeleccionado(TipoPlantaEntity tipoPlantaSeleccionado) {
        this.tipoPlantaSeleccionado = tipoPlantaSeleccionado;
    }

    public List<PisoEntity> getPisoList() {
        return pisoList;
    }

    public void setPisoList(List<PisoEntity> pisoList) {
        this.pisoList = pisoList;
    }

    public TorreEntity getTorreSeleccionada() {
        return torreSeleccionada;
    }

    public void setTorreSeleccionada(TorreEntity torreSeleccionada) {
        this.torreSeleccionada = torreSeleccionada;
    }

    public PisoEntity getPisoSeleccionado() {
        return pisoSeleccionado;
    }

    public void setPisoSeleccionado(PisoEntity pisoSeleccionado) {
        this.pisoSeleccionado = pisoSeleccionado;
    }

    public UploadedFile getUploadedTipoInmueble() {
        return uploadedTipoInmueble;
    }

    public void setUploadedTipoInmueble(UploadedFile uploadedTipoInmueble) {
        this.uploadedTipoInmueble = uploadedTipoInmueble;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public List<MunicipioEntity> getMunicipioList() {
        return municipioList;
    }

    public void setMunicipioList(List<MunicipioEntity> municipioList) {
        this.municipioList = municipioList;
    }

    public Kit getKit() {
        return kit;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public List<Kit> getKitList() {
        return kitList;
    }

    public void setKitList(List<Kit> kitList) {
        this.kitList = kitList;
    }

    public List<DepartamentoEntity> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<DepartamentoEntity> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public Archivo getArchivoTipoInmueble() {
        return archivoTipoInmueble;
    }

    public void setArchivoTipoInmueble(Archivo archivoTipoInmueble) {
        this.archivoTipoInmueble = archivoTipoInmueble;
    }
    
    public void inicioProyectos(){
        this.proyecto = new ProyectoEntity();
        this.proyectoEntityList = proyectoService.findAll();
    }

    public void prepareNewProyecto() {
        reset();
        this.proyecto = new ProyectoEntity();
        this.estadoProyectoList = estadoProyectoService.findAll();
        this.departamentoList = departamentoService.findAll();
    }
    
    public void inicioVistaNuevo() {
        reset();
        this.proyecto = new ProyectoEntity();
        this.estadoProyectoList = estadoProyectoService.findAll();
        this.departamentoList = departamentoService.findAll();
        this.allEmpresasList = empresaService.findAll();
    }

    public void persist() {
        
        if(proyecto.getOferta() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar una oferta inicial","Debe seleccionar una oferta inicial"));
            return;
        }
        try {
            if ((archivo != null) && (archivo.getNombre() != null)) {
                proyecto.setLogo(archivo.getNombre());
                proyecto.setArchivo(archivo);
            }
            proyecto = proyectoService.saveAndFlush(proyecto);
            prepareNewProyecto();

            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Proyecto guardado con éxito"));
            FacesContext.getCurrentInstance().getExternalContext().redirect("proyecto.xhtml");

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public String delete() {

        String message;

        try {
            proyectoService.remove(proyecto);
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
        proyecto = null;
        allOfertasList = null;
        allEmpresasList = null;
        allPobladosList = null;
        municipioList = null;
        departamento = null;
        municipio = null;
    }

    // Get a List of all oferta
    public List<OfertaEntity> getOfertas() {
        if (this.allOfertasList == null) {
            this.allOfertasList = ofertaService.findAll();
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
            this.allEmpresasList = empresaService.findAll();
        }
        return this.allEmpresasList;
    }

    // Update empresa of the current proyecto
    public void updateempresa(EmpresaEntity empresa) {
        this.proyecto.setEmpresa(empresa);
        // Maybe we just created and assigned a new empresa. So reset the allEmpresaList.
        allEmpresasList = null;
    }

    public List<PobladoEntity> getPoblados() {
        if (this.allPobladosList == null) {
            this.allPobladosList = pobladoService.findByMunicipio(municipio);
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
        return this.proyecto;
    }

    public void setProyecto(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
    }

    public void inicioListaProyecto() {
        this.estadoProyectoSel = estadoProyectoService.findOptionalByCodigo("01");
        this.estadoProyectoList = estadoProyectoService.findAll();
        this.proyectoEntityList = null;
    }

    public void inicioVistaProyecto() {
        this.kit = new Kit();
        this.torreNueva = new TorreEntity();
        this.tipoInmueble = new TipoInmuebleEntity();
        this.tipoPlanta = new TipoPlantaEntity();
        this.inmuebleSeleccionado = new InmuebleEntity();
        this.proyecto = proyectoService.findBy(this.proyectoId);
        this.torreListProyecto = torreService.findByProyecto(this.proyecto);
        this.kitList = kitService.findByProyecto(this.proyecto);
        this.inmuebleEntityList = null;
        this.imagenTipoInmueble = applicationBean.webCarpeta() + "imagenes/noimagen.png";
        this.imagenTipoPlanta = applicationBean.webCarpeta() + "imagenes/noimagen.png";
    }

    public void inicioListaInmueble() {
        this.torreNueva = new TorreEntity();
        this.proyecto = proyectoService.findBy(this.proyectoId);
        this.estadoInmuebleSel = estadoInmuebleService.findOptionalByCodigo("01");
        this.estadoInmuebleList = estadoInmuebleService.findAll();
        this.torreListProyecto = torreService.findByProyecto(this.proyecto);

        if (this.estadoInmuebleSel == null) {
            inmuebleService.findByProyecto(proyecto);
        } else {
            this.inmuebleEntityList = inmuebleService.findByProyectoAndEstadoInmueble(proyecto, estadoInmuebleSel);
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
        return this.proyectoEntityList;
    }

    public void setProyectoEntityList(List<ProyectoEntity> proyectoEntityList) {
        this.proyectoEntityList = proyectoEntityList;
    }

    public void cambioEstadoProyecto() {
        if (this.estadoProyectoSel == null) {
            this.proyectoEntityList = proyectoService.findAll();
        } else {
            this.proyectoEntityList = proyectoService.findByEstadoProyecto(estadoProyectoSel);
        }
    }
    
    public String modificaNegociacion(InmuebleEntity inmueble){
        return "/pages/vendedor/negociacion?faces-redirect=true&amp;id=".concat(inmueble.getId().toString());
    }

    public String redireccionaNegociacion(InmuebleEntity inmueble) {
        String ruta = "";
        if (inmueble.getEstadoInmueble().getId() == 1) {
            ruta = "/pages/vendedor/negociacion?faces-redirect=true&amp;id=".concat(inmueble.getId().toString());
        } else {
            NegociacionEntity n = negociacionService.findOptionalByInmueble(inmueble);
            if (n != null) {
                ruta = "/pages/vendedor/negociacionView?faces-redirect=true&amp;id=".concat(n.getId().toString());
            }
        }
        return ruta;
    }

    public void seleccionarPoblado(SelectEvent event) {
        this.proyecto.setPoblado((PobladoEntity) event.getObject());
    }

    /*public UploadedFile getArchivoLogo() {
        return archivoLogo;
    }

    public void setArchivoLogo(UploadedFile archivoLogo) {
        this.archivoLogo = archivoLogo;
    }*/
    private ProyectoEntity proyectoTorre;
    private TorreEntity torreNueva;
    private TorreEntity torreInstance;
    private List<TorreEntity> torreListProyecto;

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

    public void actualizarLista(SelectEvent event) {
        inicioListaInmueble();
    }

    public void cerrarDialogoCrearProyecto() {
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void dialogoTorreProyecto() {

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", "80%");
        options.put("height", 400);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        options.put("closable", true);
        List<String> paramList = new ArrayList<String>();
        paramList.add(this.proyecto.getId().toString());
        Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
        paramMap.put("id", paramList);

        RequestContext.getCurrentInstance().openDialog("/pages/proyecto/crearTorreProyectoInclude.", options, paramMap);

        inicioListaInmueble();

        RequestContext.getCurrentInstance().update(":inmuebleDataForm:inmuebleTable");
    }

    public List<TorreEntity> getTorreListProyecto() {
        return torreListProyecto;
    }

    public void setTorreListProyecto(List<TorreEntity> torreListProyecto) {
        this.torreListProyecto = torreListProyecto;
    }

    public void resetTorreProyecto() {
        this.torreNueva = new TorreEntity();
        this.torreListProyecto = torreService.findByProyecto(this.proyecto);
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

    public void grabarTorre() {
        torreNueva.setProyecto(proyecto);
        torreNueva = torreService.save(torreNueva);

        PisoEntity piso;

        for (int i = torreNueva.getPisoInicial(); i <= torreNueva.getNumeroPisos(); i++) {

            piso = pisoService.findOptionalByNumeroAndTorre(i, torreNueva);
            if (piso == null) {
                piso = new PisoEntity();
                piso.setNumero(i);
                piso.setTorre(torreNueva);
                piso = pisoService.save(piso);
            }
        }

        resetTorreProyecto();
    }

    public void grabarTipoInmueble() {
        tipoInmueble.setProyecto(proyecto);
        if ((archivoTipoInmueble != null) && (archivoTipoInmueble.getId() != null)) {
            tipoInmueble.setArchivo(archivoTipoInmueble);
        }
        tipoInmueble = tipoInmuebleService.saveAndFlush(tipoInmueble);
        archivoTipoInmueble = new Archivo();
        tipoInmueble = new TipoInmuebleEntity();
        tipoInmuebleList = tipoInmuebleService.findByProyecto(proyecto);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo Inmueble guardado con éxito"));
    }

    public void seleccionarTipoInmueble(TipoInmuebleEntity tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
        this.archivoTipoInmueble = tipoInmueble.getArchivo();        
    }

    public List<TipoInmuebleEntity> getTipoInmuebleList() {
        if (tipoInmuebleList == null) {
            tipoInmuebleList = tipoInmuebleService.findByProyecto(proyecto);
        }
        return tipoInmuebleList;
    }

    public void setTipoInmuebleList(List<TipoInmuebleEntity> tipoInmuebleList) {
        this.tipoInmuebleList = tipoInmuebleList;
    }

    public void seleccionarTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
        this.tipoPlantaSeleccionado = tipoPlanta;
        this.tipoPlantaDetalleList = tipoPlantaDetalleService.findByTipoPlanta(tipoPlantaSeleccionado);
        this.archivoTipoPlanta = tipoPlanta.getImagen();

        if (this.archivoTipoPlanta != null) {
            this.imagenTipoPlanta = applicationBean.webCarpeta() + "proyecto/" + archivoTipoPlanta.getNombre();
        } else {
            this.imagenTipoPlanta = applicationBean.webCarpeta() + "imagenes/noimagen.png";
        }
    }

    public void grabarTipoPlanta() {
        if ((archivoTipoPlanta != null) && (archivoTipoPlanta.getId() != null)) {
            tipoPlanta.setImagen(archivoTipoPlanta);
        }

        tipoPlanta.setProyecto(proyecto);
        tipoPlanta = tipoPlantaService.save(tipoPlanta);
        tipoPlantaSeleccionado = tipoPlanta;

        TipoPlantaDetalleEntity detalle;
        for (int i = 0; i < tipoPlanta.getNumeroInmuebles(); i++) {
            detalle = tipoPlantaDetalleService.findOptionalByTipoPlantaAndNumero(tipoPlanta, i + 1);
            if (null == detalle) {
                detalle = new TipoPlantaDetalleEntity();
                detalle.setNumero(i + 1);
                detalle.setTipoPlanta(tipoPlanta);
                tipoPlantaDetalleService.save(detalle);
            }
        }

        tipoPlantaDetalleList = tipoPlantaDetalleService.findByTipoPlanta(tipoPlantaSeleccionado);
        tipoPlanta = new TipoPlantaEntity();
        imagenTipoPlanta = applicationBean.webCarpeta() + "imagenes/noimagen.png";

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Tipo Planta guardado con éxito"));
    }

    public List<TipoPlantaEntity> getTipoPlantas() {
        return tipoPlantaService.findByProyecto(proyecto);
    }

    public List<TipoPlantaDetalleEntity> getTipoPlantaDetalleList() {

        return tipoPlantaDetalleList;
    }

    public void setTipoPlantaDetalleList(List<TipoPlantaDetalleEntity> tipoPlantaDetalleList) {
        this.tipoPlantaDetalleList = tipoPlantaDetalleList;
    }

    public void seleccionarTipoPlantaDetalle() {
        if (tipoPlantaSeleccionado != null) {
            tipoPlantaDetalleList = tipoPlantaDetalleService.findByTipoPlanta(tipoPlantaSeleccionado);
        }
    }

    public void guardarDetalleTipoPlanta() {
        
        for (TipoPlantaDetalleEntity tpde : tipoPlantaDetalleList) {
            tpde = tipoPlantaDetalleService.saveAndFlushAndRefresh(tpde);
            
           
            
            System.err.println(tpde.getTipoInmueble());
        }

        this.tipoPlantaDetalleList = null;
        //this.tipoPlantaDetalleList = tipoPlantaDetalleService.findByTipoPlanta(tipoPlantaSeleccionado);        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle Guardado con éxito"));
    }

    public void seleccionarTorre() {
        if (torreSeleccionada != null) {
            pisoList = pisoService.findByTorre(torreSeleccionada);
        }
    }

    public void guardarDetallePisos() {

        for (PisoEntity p : pisoList) {
            p = pisoService.save(p);
            System.err.println(p);
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle de pisos guardado con éxito"));
        pisoList = null;
    }

    public void generarInmuebles(PisoEntity piso) {
        List<TipoPlantaDetalleEntity> detalleList = tipoPlantaDetalleService.
                findByTipoPlanta(piso.getTipoPlanta());

        InmuebleEntity inmueble;
        EstadoInmuebleEntity estadoInmueble = estadoInmuebleService.
                findOptionalByNombre(Constantes.INMUEBLE_DISPONIBLE);

        Integer numeroInmueble;

        for (TipoPlantaDetalleEntity detalle : detalleList) {
            numeroInmueble = (piso.getNumero() * 100) + detalle.getNumero();
            
            //verificamos si el inmueble existe...
            inmueble = inmuebleService.findOptionalByPisoAndNumero(piso, numeroInmueble.toString());

            if (inmueble == null) {
                inmueble = new InmuebleEntity();
                inmueble.setArea(detalle.getTipoInmueble().getArea());
                inmueble.setPiso(piso);
                inmueble.setProyecto(piso.getTorre().getProyecto());
                inmueble.setValorMetroCuadrado(detalle.getTipoInmueble().getValorMetroCuadrado());
                inmueble.setValorSeparacion(detalle.getTipoInmueble().getValorSeparacion());
                inmueble.setEstadoInmueble(estadoInmueble);
                inmueble.setNumero(numeroInmueble.toString());
                inmueble.setTipoInmueble(detalle.getTipoInmueble());
                inmueble.setValorTotal(detalle.getTipoInmueble().getValorMetroCuadrado() * detalle.getTipoInmueble().getArea());
                inmueble = inmuebleService.save(inmueble);
            }
        }

        inmuebleEntityList = inmuebleService.findByPiso(piso);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inmuebles generados con éxito!!"));
    }

    public void seleccionarPiso() {
        if (pisoSeleccionado != null) {
            inmuebleEntityList = inmuebleService.
                    findByPiso(pisoSeleccionado);
        }
    }

    public void uploadArchivoTipoInmueble(FileUploadEvent event) {
        archivoTipoInmueble = archivoService.upload("imagenes", "tipo_inmueble", event.getFile());
    }

    public void uploadArchivoTipoPlanta(FileUploadEvent event) {
        archivoTipoPlanta = archivoService.upload("imagenes", "tipo_planta", event.getFile());
    }

    public void uploadLogoProyecto(FileUploadEvent event) {
        archivo = archivoService.upload("imagenes", "proyecto", event.getFile());
    }

    public void departamentoChange() {
        municipioList = municipioService.findByDepartamento(departamento);
        municipio = null;
        allPobladosList.clear();
        allPobladosList = null;
        System.err.println(departamento.getNombre());
    }

    public void municipioChange() {
        allPobladosList = pobladoService.findByMunicipio(municipio);
        System.err.println(municipio.getNombre());
    }

    public void seleccionar(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
        this.archivo = proyecto.getArchivo();
        if (proyecto.getPoblado() != null) {
            this.departamento = proyecto.getPoblado().getMunicipio().getDepartamento();
            this.municipioList = municipioService.findByDepartamento(this.departamento);
            this.municipio = proyecto.getPoblado().getMunicipio();
            this.allPobladosList = pobladoService.findByMunicipio(this.municipio);
        } else {
            this.departamento = null;
            this.municipioList = null;
            this.municipio = null;
            this.allPobladosList = null;
        }

    }

    public void seleccionarEmpresa() {
        if (this.empresaSeleccionada == null) {
            proyectoEntityList = proyectoService.findByEmpresaIsNull();
        } else {
            proyectoEntityList = proyectoService.findByEmpresa(this.empresaSeleccionada);
        }
    }

    public void detallePisoSeleccionarTipoPlanta(PisoEntity piso) {
        piso = pisoService.saveAndFlush(piso);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Detalle piso modificado con éxito!!"));
    }

    public void seleccionarInmueble(InmuebleEntity inmuebleEntity) {
        this.inmuebleSeleccionado = inmuebleEntity;
    }

    public void guardarInmueble() {
        this.inmuebleSeleccionado = inmuebleService.saveAndFlush(inmuebleSeleccionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Inmueble guardado con éxito"));
        this.inmuebleSeleccionado = new InmuebleEntity();
    }

    public void valorTotal() {

        double tempo = inmuebleSeleccionado.getValorMetroCuadrado() * inmuebleSeleccionado.getArea();

        if (inmuebleSeleccionado.getIncremento() != null) {
            tempo += inmuebleSeleccionado.getIncremento();
        }
        inmuebleSeleccionado.setValorTotal(tempo);
    }

    public void seleccionarKit(Kit kit) {
        this.kit = kit;
    }

    public void guardarKit() {
        this.kit.setProyecto(this.proyecto);
        this.kit = kitService.saveAndFlush(kit);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Kit guardado con éxito"));
        this.kit = new Kit();
        this.kitList = kitService.findByProyecto(this.proyecto);
    }

    public void seleccionarModificarTorre(TorreEntity torre) {
        this.torreNueva = torre;
    }
    
    public void inicioVistaEditar(){
        this.proyecto = proyectoService.findBy(this.proyectoId); 
        this.archivo = proyecto.getArchivo();
        this.estadoProyectoList = estadoProyectoService.findAll();
        this.departamentoList = departamentoService.findAll();
        this.allEmpresasList = empresaService.findAll();
        
        if (proyecto.getPoblado() != null) {
            this.departamento = proyecto.getPoblado().getMunicipio().getDepartamento();
            this.municipioList = municipioService.findByDepartamento(this.departamento);
            this.municipio = proyecto.getPoblado().getMunicipio();
            this.allPobladosList = pobladoService.findByMunicipio(this.municipio);
        } else {
            this.departamento = null;
            this.municipioList = null;
            this.municipio = null;
            this.allPobladosList = null;
        }        
    }
    
    public void eliminarDetalleTipoPlanta(TipoPlantaDetalleEntity detalle){
        tipoPlantaDetalleService.attachAndRemove(detalle);
        tipoPlantaDetalleList = tipoPlantaDetalleService.findByTipoPlanta(tipoPlantaSeleccionado);
    }
    
    public List<EmpresaEntity> getAllEmpresasList() {
        return allEmpresasList;
    }

    public void setAllEmpresasList(List<EmpresaEntity> allEmpresasList) {
        this.allEmpresasList = allEmpresasList;
    }
    

}
