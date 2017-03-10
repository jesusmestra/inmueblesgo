/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.service.NegociacionService;
import com.j2km.inmueblesgo.service.NegociacionTerceroService;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author jkelsy
 */
//@Named("dashboard")
@ManagedBean(name = "dashboard")
@SessionScoped
public class DashboardBean implements Serializable {

    @Inject
    private NegociacionService negociacionService;

    @Inject
    private NegociacionTerceroService nts;

    private List<NegociacionEntity> nuevasNegociaciones;
    private int tablaActiva = 0;

    public int getTablaActiva() {
        return tablaActiva;
    }

    public void setTablaActiva(int tablaActiva) {
        this.tablaActiva = tablaActiva;
    }

    public List<NegociacionEntity> getNuevasNegociaciones() {

        if (nuevasNegociaciones == null) {
            nuevasNegociaciones = negociacionService.findAllNuevas();
        }
        return nuevasNegociaciones;
    }

    public void setNuevasNegociaciones(List<NegociacionEntity> nuevasNegociaciones) {
        this.nuevasNegociaciones = nuevasNegociaciones;
    }

    public void cargarNuevasNegociaciones(int numeroTabla) {
        if (tablaActiva == numeroTabla) {
            tablaActiva = 0;
        } else {
            tablaActiva = numeroTabla;
        }

    }

    public List<NegociacionTerceroEntity> findAllTerceroByNegociacio(NegociacionEntity negociacionEntity) {
        return nts.findAllNegociacionTerceroByNegociacion(negociacionEntity);
    }

}