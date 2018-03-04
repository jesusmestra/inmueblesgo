package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.Pago;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.NegociacionTerceroRepository;
import com.j2km.inmueblesgo.service.PagoRepository;
import com.j2km.inmueblesgo.service.PlanPagoRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named("pagoBean")
@ViewScoped
public class PagoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(PagoBean.class.getName());
    
    @Inject private NegociacionTerceroRepository negociacionTerceroRepository; 
    @Inject private PagoRepository pagoRepository;
    @Inject private UsuarioRepository usuarioRepository;
    @Inject private PlanPagoRepository planPagoRepository;
    
    private TerceroEntity cliente;
    private Pago pago;
    private NegociacionEntity negociacion;
    private UsuarioEntity usuario;
    
    private List<NegociacionTerceroEntity> negociaciones;
    private List<PlanPagoEntity> planPagoList;

    public double totalPagado(){
        double temporal = 0;
        for(Pago p: pagos){
            temporal+= p.getValor();
        }
        return temporal;
    }
    public List<PlanPagoEntity> getPlanPagoList() {
        return planPagoList;
    }

    public void setPlanPagoList(List<PlanPagoEntity> planPagoList) {
        this.planPagoList = planPagoList;
    }
    private List<Pago> pagos;

    public NegociacionEntity getNegociacion() {
        return negociacion;
    }

    public void setNegociacion(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
    }

    public TerceroEntity getCliente() {
        return cliente;
    }

    public void setCliente(TerceroEntity cliente) {
        this.cliente = cliente;
    }

    public List<NegociacionTerceroEntity> getNegociaciones() {
        return negociaciones;
    }

    public void setNegociaciones(List<NegociacionTerceroEntity> negociaciones) {
        this.negociaciones = negociaciones;
    }
    
    public void seleccionarTercero(SelectEvent event) {
        this.cliente = (TerceroEntity) event.getObject();
        negociaciones = negociacionTerceroRepository.findByTercero(this.cliente);    
    }
    
    public void cargarPagos(){        
        pagos = pagoRepository.findByNegociacion(negociacion);
        planPagoList = planPagoRepository.findByNegociacion(negociacion);
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
    
    @PostConstruct
    public void inicio(){
        this.pago = new Pago();
        this.usuario = usuarioRepository.findOptionalByLogin(
                                    FacesContext.getCurrentInstance().
                                    getExternalContext().
                                    getRemoteUser());
    }
    
    public void guardarPago(){
        pago.setFecha(new Date());
        pago.setUsuario(usuario);
        pago.setNegociacion(negociacion);
        pago = pagoRepository.saveAndFlush(pago);        
        pagos = pagoRepository.findByNegociacion(negociacion);
        pago = new Pago();
    }
}
