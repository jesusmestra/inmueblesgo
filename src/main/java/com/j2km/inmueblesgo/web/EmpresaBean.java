package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.Archivo;
import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.RepresentanteEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.service.ArchivoRepository;
import com.j2km.inmueblesgo.service.ConfiguracionService;
import com.j2km.inmueblesgo.service.DepartamentoRepository;
import com.j2km.inmueblesgo.service.EmpresaRepository;
import com.j2km.inmueblesgo.service.MunicipioRepository;
import com.j2km.inmueblesgo.service.PobladoRepository;
import com.j2km.inmueblesgo.service.RepresentanteRepository;
import com.j2km.inmueblesgo.service.TerceroRepository;
import com.j2km.inmueblesgo.web.util.MessageFactory;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

@Named("empresaBean")
@ViewScoped
public class EmpresaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(EmpresaBean.class.getName());
    
    private EmpresaEntity empresa;
    
    @Inject
    private EmpresaRepository empresaService;
    
    @Inject
    private PobladoRepository pobladoService;
    
    @Inject
    private ArchivoRepository archivoService;

    @Inject
    private TerceroRepository terceroService;
    
    @Inject
    private MunicipioRepository municipioService;
    
    @Inject
    private DepartamentoRepository departamentoService;
    
    @Inject
    private RepresentanteRepository representanteService;
    
    private List<EmpresaEntity> allEmpresaList;    
    private List<PobladoEntity> allPobladosList;
    
    private MunicipioEntity municipioBusqueda;
    
    private List<MunicipioEntity> municipioList;
    private List<DepartamentoEntity> departamentoList;
    
    //para los select del poblado
    private DepartamentoEntity departamento;
    private MunicipioEntity municipio;

    //archivo para cargar el logo
    private Archivo archivo;
    
    //datos para buscar los representantes
    private String identificacion;
    private List<RepresentanteEntity> representanteList;

    public List<RepresentanteEntity> getRepresentanteList() {
        return representanteList;
    }

    public void setRepresentanteList(List<RepresentanteEntity> representanteList) {
        this.representanteList = representanteList;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    public void seleccionarRepresentante(SelectEvent event) {
        RepresentanteEntity re = new RepresentanteEntity();
        re.setEmpresa(this.empresa);
        re.setTercero((TerceroEntity) event.getObject());
        this.representanteList.add(re);        
    }

    public List<MunicipioEntity> getMunicipioList() {
        return municipioList;
    }

    public void setMunicipioList(List<MunicipioEntity> municipioList) {
        this.municipioList = municipioList;
    }

    public List<DepartamentoEntity> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<DepartamentoEntity> departamentoList) {
        this.departamentoList = departamentoList;
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
    
    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public List<EmpresaEntity> getAllEmpresaList() {        
        allEmpresaList = empresaService.findAll();        
        return allEmpresaList;
    }

    public void setAllEmpresaList(List<EmpresaEntity> allEmpresaList) {
        this.allEmpresaList = allEmpresaList;
    }
    
    @Inject
    private ConfiguracionService configuracionServiceInstance;
    
    public void prepareNewEmpresa() {        
        this.empresa = new EmpresaEntity();   
        this.departamentoList = departamentoService.findAll();
        this.departamento = null;
        this.municipio = null;            
        this.municipioList = null;
        this.allPobladosList = null; 
        this.archivo = new Archivo();
        this.representanteList = new ArrayList<>();
    }

    public String persist() throws IOException {
        String message;        
        try {
            empresa.setLogo(archivo);
            empresa = empresaService.save(empresa);
            
            for (RepresentanteEntity representante : representanteList) {
                representanteService.saveAndFlush(representante);
            }
            
            message = "Empresa guardada con éxito!!!";
            prepareNewEmpresa();
            
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
        
        FacesContext.getCurrentInstance().addMessage(message, new FacesMessage(message));        
        return null;
    }
    
    public String delete() {
        
        String message;
        
        try {
            empresaService.remove(empresa);
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
    
    
    public List<PobladoEntity> getPoblados() {
        if (this.allPobladosList == null) {
            this.allPobladosList = pobladoService.findByMunicipio(municipio);
        }
        return this.allPobladosList;
    }
    
    // Update poblado of the current empresa
    public void updatePoblado(PobladoEntity poblado) {
        this.empresa.setPoblado(poblado);
        // Maybe we just created and assigned a new poblado. So reset the allPobladoList.
        allPobladosList = null;
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
    
    public void seleccionarPoblado(SelectEvent event) {
        this.empresa.setPoblado((PobladoEntity) event.getObject());
    }
    
    public void uploadLogoEmpresa(FileUploadEvent event){
        
        UploadedFile archivoLogo;        
        archivoLogo = event.getFile();
        
        if (null != archivoLogo) {
            try {
                archivo = new Archivo();
                archivo = archivoService.save(archivo);
                
                String nombreLogo = configuracionServiceInstance.
                        copiarArchivo(archivoLogo, "empresa_logo_" + archivo.getId().toString(), "empresa");                
                
                archivo.setNombre(nombreLogo);                
                archivo.setExtension(FilenameUtils.getExtension(nombreLogo));
                archivo = archivoService.save(archivo);
                
            } catch (IOException ex) {
                Logger.getLogger(ProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.err.println("mierda no existe");
        }
    }
    
    public void seleccionarEmpresa(EmpresaEntity empresa){
        this.empresa = empresa;
        this.archivo = empresa.getLogo();
        this.representanteList = representanteService.findByEmpresa(empresa);
        if(empresa.getPoblado() != null){
            this.departamento = empresa.getPoblado().getMunicipio().getDepartamento();
            this.municipio = empresa.getPoblado().getMunicipio();            
            this.municipioList = municipioService.findByDepartamento(this.departamento);
            this.allPobladosList = pobladoService.findByMunicipio(this.municipio);            
        }else{
            this.departamento = null;
            this.municipio = null;            
            this.municipioList = null;
            this.allPobladosList = null; 
        }
    }
    
    public void departamentoChange(){
        municipioList = municipioService.findByDepartamento(departamento);
        municipio = null;
        allPobladosList.clear();
        allPobladosList = null;
        System.err.println(departamento.getNombre());       
    }
    
    public void municipioChange(){
        allPobladosList = pobladoService.findByMunicipio(municipio);
        System.err.println(municipio.getNombre());        
    }
    
    public List<RepresentanteEntity> representantesPorEmpresa(EmpresaEntity empresa){
        return representanteService.findByEmpresa(empresa);
    }

}
