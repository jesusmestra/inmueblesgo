package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.NotificacionVendedorEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.TipoFuenteInformacionEntity;
import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.DepartamentoRepository;
import com.j2km.inmueblesgo.service.MunicipioRepository;
import com.j2km.inmueblesgo.service.NotificacionVendedorRepository;
import com.j2km.inmueblesgo.service.PobladoRepository;
import com.j2km.inmueblesgo.service.TerceroRepository;
import com.j2km.inmueblesgo.service.TipoFuenteInformacionRepository;
import com.j2km.inmueblesgo.service.TipoIdentificacionRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
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
import org.primefaces.event.SelectEvent;

@Named("terceroBean")
@ViewScoped
public class TerceroBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(TerceroBean.class.getName());

    @Inject private TerceroRepository terceroService;
    @Inject private UsuarioRepository usuarioService;
    @Inject private TipoIdentificacionRepository tipoIdentificacionService;
    @Inject private PobladoRepository pobladoService;    
    @Inject private DepartamentoRepository departamentoService;    
    @Inject private MunicipioRepository municipioService;    
    @Inject private TipoFuenteInformacionRepository tipoFuenteInformacionService;
    @Inject private NotificacionVendedorRepository notificacionVendedorRepository;
    
    //Departamentos
    private List<DepartamentoEntity> departamentoList;
    
    //Lugar de expedicion
    private DepartamentoEntity lugarExpedicionDepartamento;
    private MunicipioEntity lugarExpedicionMunicipio;
    private List<MunicipioEntity> lugarExpedicionMunicipioList;
    private List<PobladoEntity> lugarExpedicionPobladoList;
    
    //Lugar de nacimiento
    private DepartamentoEntity lugarNacimientoDepartamento;
    private MunicipioEntity lugarNacimientoMunicipio;
    private List<MunicipioEntity> lugarNacimientoMunicipioList;
    private List<PobladoEntity> lugarNacimientoPobladoList; 
    
    //Lugar de residencia
    private DepartamentoEntity lugarResidenciaDepartamento;
    private MunicipioEntity lugarResidenciaMunicipio;
    private List<MunicipioEntity> lugarResidenciaMunicipioList;
    private List<PobladoEntity> lugarResidenciaPobladoList; 

    private TerceroEntity tercero;
    private List<TipoIdentificacionEntity> allTipoIdentificacionsList;
    private List<TipoFuenteInformacionEntity> tipoFuenteInformacionList;
    private List<TerceroEntity> allTerceros;
    private List<TerceroEntity> tercerosPorUsuario;
    
    private NotificacionVendedorEntity notificacionVendedor;
    private List<NotificacionVendedorEntity> notificacionVendedorList;

    public NotificacionVendedorEntity getNotificacionVendedor() {
        return notificacionVendedor;
    }

    public void setNotificacionVendedor(NotificacionVendedorEntity notificacionVendedor) {
        this.notificacionVendedor = notificacionVendedor;
    }

    public List<NotificacionVendedorEntity> getNotificacionVendedorList() {
        return notificacionVendedorList;
    }

    public void setNotificacionVendedorList(List<NotificacionVendedorEntity> notificacionVendedorList) {
        this.notificacionVendedorList = notificacionVendedorList;
    }

    public void prepareNewTercero() {
        reset();
        this.tercero = new TerceroEntity();
        this.departamentoList = departamentoService.findAll();
        this.tipoFuenteInformacionList = tipoFuenteInformacionService.findAll();
        // set any default values now, if you need
        // Example: this.tercero.setAnything("test");
    }
    
    public void selectTabNotificacion(TerceroEntity cliente){
        this.notificacionVendedor = new NotificacionVendedorEntity();
        this.notificacionVendedor.setCliente(cliente);
         
        UsuarioEntity usuario = usuarioService.findOptionalByLogin(
                                    FacesContext.getCurrentInstance().
                                    getExternalContext().
                                    getRemoteUser());
       
        this.notificacionVendedorList = notificacionVendedorRepository.findByVendedorAndCliente(usuario, cliente);
    }
    
    public void guardarNotificacionVendedor(){
        UsuarioEntity usuario = usuarioService.findOptionalByLogin(
                                    FacesContext.getCurrentInstance().
                                    getExternalContext().
                                    getRemoteUser());
        notificacionVendedor.setVendedor(usuario);
        notificacionVendedor.setActiva(Boolean.TRUE);
        notificacionVendedor = notificacionVendedorRepository.saveAndFlush(notificacionVendedor);
        
    }

    public List<TerceroEntity> getTercerosPorUsuario() {

        UsuarioEntity usuarioEntity;

        if (FacesContext.getCurrentInstance().getExternalContext().isUserInRole("ADMIN")) {
            tercerosPorUsuario = terceroService.findAll();
        } else {
            tercerosPorUsuario = terceroService.findByUsuario(
                    usuarioService.findOptionalByLogin(
                            FacesContext.
                                    getCurrentInstance().
                                    getExternalContext().
                                    getRemoteUser()
                    )
            );
        }

        return tercerosPorUsuario;
    }

    public List<TipoFuenteInformacionEntity> getTipoFuenteInformacionList() {
        return tipoFuenteInformacionList;
    }

    public void setTipoFuenteInformacionList(List<TipoFuenteInformacionEntity> tipoFuenteInformacionList) {
        this.tipoFuenteInformacionList = tipoFuenteInformacionList;
    }

    public void setTercerosPorUsuario(List<TerceroEntity> tercerosPorUsuario) {
        this.tercerosPorUsuario = tercerosPorUsuario;
    }

    public List<TerceroEntity> getAllTerceros() {
        allTerceros = terceroService.findAll();
        return allTerceros;
    }

    public void setAllTerceros(List<TerceroEntity> allTerceros) {
        this.allTerceros = allTerceros;
    }

    public String persist() {

        String message;

        try {
            if (tercero.getId() == null) {
                UsuarioEntity vendedor = usuarioService.findOptionalByLogin(
                        FacesContext.
                                getCurrentInstance().
                                getExternalContext().
                                getRemoteUser()
                );

                tercero.setUsuario(vendedor);
            }
            tercero = terceroService.saveAndFlush(tercero);
            tercero = new TerceroEntity();
            
            lugarExpedicionPobladoList = null;
            lugarExpedicionMunicipioList = null;
            lugarExpedicionDepartamento = null;
            
            lugarNacimientoPobladoList = null;
            lugarNacimientoMunicipioList = null;
            lugarNacimientoDepartamento = null;
            
            lugarResidenciaPobladoList = null;
            lugarResidenciaMunicipioList = null;
            lugarResidenciaDepartamento = null;
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

    public String delete() {

        String message;

        try {
            terceroService.remove(tercero);
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

    public void seleccionarTercero(TerceroEntity tercero) {        
        this.tercero = tercero;
        if(tercero.getLugarExpedicion()!= null){
            lugarExpedicionDepartamento = tercero.getLugarExpedicion().getMunicipio().getDepartamento();
            lugarExpedicionMunicipio = tercero.getLugarExpedicion().getMunicipio();
            lugarExpedicionMunicipioList = municipioService.findByDepartamento(lugarExpedicionDepartamento);
            lugarExpedicionPobladoList = pobladoService.findByMunicipio(lugarExpedicionMunicipio);            
        }
        
        if(tercero.getLugarNacimiento()!= null){
            lugarNacimientoDepartamento = tercero.getLugarNacimiento().getMunicipio().getDepartamento();
            lugarNacimientoMunicipio = tercero.getLugarNacimiento().getMunicipio();
            lugarNacimientoMunicipioList = municipioService.findByDepartamento(lugarNacimientoDepartamento);
            lugarNacimientoPobladoList = pobladoService.findByMunicipio(lugarNacimientoMunicipio);            
        }
        
        if(tercero.getLugarResidencia()!= null){
            lugarResidenciaDepartamento = tercero.getLugarResidencia().getMunicipio().getDepartamento();
            lugarResidenciaMunicipio = tercero.getLugarResidencia().getMunicipio();
            lugarResidenciaMunicipioList = municipioService.findByDepartamento(lugarResidenciaDepartamento);
            lugarResidenciaPobladoList = pobladoService.findByMunicipio(lugarResidenciaMunicipio);            
        }
    }

    public void reset() {
        tercero = null;       
        allTipoIdentificacionsList = null;

    }

    // Get a List of all tipoIdentificacion
    public List<TipoIdentificacionEntity> getTipoIdentificacions() {
        if (this.allTipoIdentificacionsList == null) {
            this.allTipoIdentificacionsList = tipoIdentificacionService.findAll();
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

    public void seleccionarPoblado(SelectEvent event) {
        this.tercero.setLugarExpedicion((PobladoEntity) event.getObject());
    }

    public List<DepartamentoEntity> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<DepartamentoEntity> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public DepartamentoEntity getLugarExpedicionDepartamento() {
        return lugarExpedicionDepartamento;
    }

    public void setLugarExpedicionDepartamento(DepartamentoEntity lugarExpedicionDepartamento) {
        this.lugarExpedicionDepartamento = lugarExpedicionDepartamento;
    }

    public MunicipioEntity getLugarExpedicionMunicipio() {
        return lugarExpedicionMunicipio;
    }

    public void setLugarExpedicionMunicipio(MunicipioEntity lugarExpedicionMunicipio) {
        this.lugarExpedicionMunicipio = lugarExpedicionMunicipio;
    }

    public List<MunicipioEntity> getLugarExpedicionMunicipioList() {
        return lugarExpedicionMunicipioList;
    }

    public void setLugarExpedicionMunicipioList(List<MunicipioEntity> lugarExpedicionMunicipioList) {
        this.lugarExpedicionMunicipioList = lugarExpedicionMunicipioList;
    }

    public List<PobladoEntity> getLugarExpedicionPobladoList() {
        return lugarExpedicionPobladoList;
    }

    public void setLugarExpedicionPobladoList(List<PobladoEntity> lugarExpedicionPobladoList) {
        this.lugarExpedicionPobladoList = lugarExpedicionPobladoList;
    }

    public DepartamentoEntity getLugarNacimientoDepartamento() {
        return lugarNacimientoDepartamento;
    }

    public void setLugarNacimientoDepartamento(DepartamentoEntity lugarNacimientoDepartamento) {
        this.lugarNacimientoDepartamento = lugarNacimientoDepartamento;
    }

    public MunicipioEntity getLugarNacimientoMunicipio() {
        return lugarNacimientoMunicipio;
    }

    public void setLugarNacimientoMunicipio(MunicipioEntity lugarNacimientoMunicipio) {
        this.lugarNacimientoMunicipio = lugarNacimientoMunicipio;
    }

    public List<MunicipioEntity> getLugarNacimientoMunicipioList() {
        return lugarNacimientoMunicipioList;
    }

    public void setLugarNacimientoMunicipioList(List<MunicipioEntity> lugarNacimientoMunicipioList) {
        this.lugarNacimientoMunicipioList = lugarNacimientoMunicipioList;
    }

    public List<PobladoEntity> getLugarNacimientoPobladoList() {
        return lugarNacimientoPobladoList;
    }

    public void setLugarNacimientoPobladoList(List<PobladoEntity> lugarNacimientoPobladoList) {
        this.lugarNacimientoPobladoList = lugarNacimientoPobladoList;
    }

    public DepartamentoEntity getLugarResidenciaDepartamento() {
        return lugarResidenciaDepartamento;
    }

    public void setLugarResidenciaDepartamento(DepartamentoEntity lugarResidenciaDepartamento) {
        this.lugarResidenciaDepartamento = lugarResidenciaDepartamento;
    }

    public MunicipioEntity getLugarResidenciaMunicipio() {
        return lugarResidenciaMunicipio;
    }

    public void setLugarResidenciaMunicipio(MunicipioEntity lugarResidenciaMunicipio) {
        this.lugarResidenciaMunicipio = lugarResidenciaMunicipio;
    }

    public List<MunicipioEntity> getLugarResidenciaMunicipioList() {
        return lugarResidenciaMunicipioList;
    }

    public void setLugarResidenciaMunicipioList(List<MunicipioEntity> lugarResidenciaMunicipioList) {
        this.lugarResidenciaMunicipioList = lugarResidenciaMunicipioList;
    }

    public List<PobladoEntity> getLugarResidenciaPobladoList() {
        return lugarResidenciaPobladoList;
    }

    public void setLugarResidenciaPobladoList(List<PobladoEntity> lugarResidenciaPobladoList) {
        this.lugarResidenciaPobladoList = lugarResidenciaPobladoList;
    }
    
    public void seleccionarDepartamentoExpedicion(){
        if(lugarExpedicionDepartamento != null){
            lugarExpedicionMunicipioList = municipioService.findByDepartamento(lugarExpedicionDepartamento);
            lugarExpedicionPobladoList = null;
        }
    }
    
    public void seleccionarMunicipioExpedicion(){
        if(lugarExpedicionMunicipio != null){
            lugarExpedicionPobladoList = pobladoService.findByMunicipio(lugarExpedicionMunicipio);
        }
    }
    
    public void seleccionarDepartamentoNacimiento(){
        if(lugarNacimientoDepartamento != null){
            lugarNacimientoMunicipioList = municipioService.findByDepartamento(lugarNacimientoDepartamento);
            lugarNacimientoPobladoList = null;
        }
    }
    
    public void seleccionarMunicipioNacimiento(){
        if(lugarNacimientoMunicipio != null){
            lugarNacimientoPobladoList = pobladoService.findByMunicipio(lugarNacimientoMunicipio);
        }
    }
    
    public void seleccionarDepartamentoResidencia(){
        if(lugarResidenciaDepartamento != null){
            lugarResidenciaMunicipioList = municipioService.findByDepartamento(lugarResidenciaDepartamento);
            lugarResidenciaPobladoList = null;
        }
    }
    
    public void seleccionarMunicipioResidencia(){
        if(lugarResidenciaMunicipio != null){
            lugarResidenciaPobladoList = pobladoService.findByMunicipio(lugarResidenciaMunicipio);
        }
    }

}
