package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.configuracion.Constantes;
import com.j2km.inmueblesgo.domain.Configuracion;
import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionHistoricoEntity;
import com.j2km.inmueblesgo.domain.NegociacionObservacion;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.service.ConfiguracionRepository;
import com.j2km.inmueblesgo.service.EstadoNegociacionRepository;
import com.j2km.inmueblesgo.service.NegociacionHistoricoRepository;
import com.j2km.inmueblesgo.service.NegociacionObservacionRepository;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.NegociacionTerceroRepository;
import com.j2km.inmueblesgo.service.PlanPagoRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("verNegociacion")
@ViewScoped
public class VerNegociacionBean implements Serializable {

    @Inject private NegociacionRepository negociacionRepository;
    @Inject private NegociacionTerceroRepository negociacionTerceroRepository;
    @Inject private PlanPagoRepository planPagoRepository;
    @Inject private NegociacionObservacionRepository negociacionObservacionRepository;
    @Inject private UsuarioRepository usuarioService;
    @Inject private EstadoNegociacionRepository estadoNegociacionRepository;
    @Inject private NegociacionHistoricoRepository negociacionHistoricoRepository;
    
    
    private NegociacionEntity negociacion;
    private Long negociacionId;
    
    private List<NegociacionTerceroEntity> negociacionTerceroList;
    private List<PlanPagoEntity> planPagoList;
    private List<NegociacionObservacion> observacionList;
    
    private String observacion;
    private Configuracion configuracion;

    public List<NegociacionObservacion> getObservacionList() {
        return observacionList;
    }

    public void setObservacionList(List<NegociacionObservacion> observacionList) {
        this.observacionList = observacionList;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<NegociacionTerceroEntity> getNegociacionTerceroList() {
        return negociacionTerceroList;
    }

    public void setNegociacionTerceroList(List<NegociacionTerceroEntity> negociacionTerceroList) {
        this.negociacionTerceroList = negociacionTerceroList;
    }

    public List<PlanPagoEntity> getPlanPagoList() {
        return planPagoList;
    }

    public void setPlanPagoList(List<PlanPagoEntity> planPagoList) {
        this.planPagoList = planPagoList;
    }

    public Long getNegociacionId() {
        return negociacionId;
    }

    public void setNegociacionId(Long negociacionId) {
        this.negociacionId = negociacionId;
    }

    public NegociacionEntity getNegociacion() {
        return negociacion;
    }

    public void setNegociacion(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
    }
    
    public void cargar(){
        negociacion = negociacionRepository.findBy(negociacionId);
        negociacionTerceroList = negociacionTerceroRepository.findByNegociacion(negociacion);
        planPagoList = planPagoRepository.findByNegociacion(negociacion);   
        observacionList = negociacionObservacionRepository.findByNegociacion(negociacion);
    }
    
    public void agregarObservacion(){
        NegociacionObservacion no = new NegociacionObservacion();
        no.setNegociacion(this.negociacion);
        no.setUsuario(usuarioService.findOptionalByLogin(
                        FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()));
        no.setObservacion(observacion);
        no.setFecha(new Date());
        no = negociacionObservacionRepository.saveAndFlush(no);
        
        observacion = "";
        observacionList = negociacionObservacionRepository.findByNegociacion(this.negociacion);
    }
    
    public String aprobar(){
        EstadoNegociacionEntity estado = estadoNegociacionRepository.findOptionalByNombre(Constantes.NEGOCIACION_APROBADA);
        this.negociacion.setEstadoNegociacion(estado);
        this.negociacion = negociacionRepository.saveAndFlush(this.negociacion);
        
        NegociacionHistoricoEntity nhe = new NegociacionHistoricoEntity();
        nhe.setCreadoPor(usuarioService.findOptionalByLogin(
                        FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()));
        nhe.setNegociacion(this.negociacion);
        nhe.setFecha(new Date());
        nhe.setEstadoNegociacion(estado);
        nhe.setObservacion("");
        nhe = negociacionHistoricoRepository.saveAndFlush(nhe);        
        
        return "/pages/main?faces-redirect=true&amp";
        
    }
    
    public String rechazar(){
        EstadoNegociacionEntity estado = estadoNegociacionRepository.findOptionalByNombre(Constantes.NEGOCIACION_RECHAZADA);
        this.negociacion.setEstadoNegociacion(estado);
        this.negociacion = negociacionRepository.saveAndFlush(this.negociacion);
        
        NegociacionHistoricoEntity nhe = new NegociacionHistoricoEntity();
        nhe.setCreadoPor(usuarioService.findOptionalByLogin(
                        FacesContext.getCurrentInstance().getExternalContext().getRemoteUser()));
        nhe.setNegociacion(this.negociacion);
        nhe.setFecha(new Date());
        nhe.setEstadoNegociacion(estado);
        nhe.setObservacion("");
        nhe = negociacionHistoricoRepository.saveAndFlush(nhe);        
        
        return "/pages/main?faces-redirect=true&amp";
        
    }
    
}
