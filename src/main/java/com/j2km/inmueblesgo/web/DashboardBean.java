/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.configuracion.Constantes;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import com.j2km.inmueblesgo.service.NegociacionRepository;
import com.j2km.inmueblesgo.service.NegociacionTerceroRepository;
import com.j2km.inmueblesgo.service.UsuarioRepository;
import java.io.Serializable;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("dashboard")
public class DashboardBean implements Serializable {

    @Inject private NegociacionRepository negociacionService;
    @Inject private NegociacionTerceroRepository nts;
    @Inject private UsuarioRepository usuarioService;

    private List<NegociacionEntity> nuevasNegociaciones;
    private List<NegociacionEntity> negociacionesPorUsuario;
    private List<NegociacionEntity> negociacionesAprobadasPorUsuario;
    private UsuarioEntity usuarioActual;

    public List<NegociacionEntity> getNegociacionesAprobadasPorUsuario() {
        return negociacionesAprobadasPorUsuario;
    }

    public void setNegociacionesAprobadasPorUsuario(List<NegociacionEntity> negociacionesAprobadasPorUsuario) {
        this.negociacionesAprobadasPorUsuario = negociacionesAprobadasPorUsuario;
    }

    public List<NegociacionEntity> getNegociacionesPorUsuario() {
        return negociacionesPorUsuario;
    }

    public void setNegociacionesPorUsuario(List<NegociacionEntity> negociacionesPorUsuario) {
        this.negociacionesPorUsuario = negociacionesPorUsuario;
    }
    
    public void cargar(){
        this.usuarioActual = usuarioService.findOptionalByLogin(FacesContext.getCurrentInstance().getExternalContext().getRemoteUser());
        
        //listado de negociaciones que verá el gerente.
        this.nuevasNegociaciones = negociacionService.findByEstadoNegociacion_nombre(Constantes.NEGOCIACION_RADICADA);
        this.negociacionesPorUsuario = negociacionService.findByVendedor(this.usuarioActual);
        this.negociacionesAprobadasPorUsuario = negociacionService.findByVendedorAndEstadoNegociacion_nombre(usuarioActual, Constantes.NEGOCIACION_APROBADA);
        
    }

    public List<NegociacionEntity> getNuevasNegociaciones() {
        return nuevasNegociaciones;     
    }

    public void setNuevasNegociaciones(List<NegociacionEntity> nuevasNegociaciones) {
        this.nuevasNegociaciones = nuevasNegociaciones;
    }

    public List<NegociacionTerceroEntity> findAllTerceroByNegociacio(NegociacionEntity negociacionEntity) {
        return nts.findByNegociacion(negociacionEntity);
    }

}
