package com.j2km.inmueblesgo.web;

import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.PisoEntity;
import com.j2km.inmueblesgo.domain.TipoInmuebleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaDetalleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import com.j2km.inmueblesgo.service.EstadoInmuebleRepository;
import com.j2km.inmueblesgo.service.InmuebleRepository;
import com.j2km.inmueblesgo.service.InmuebleService;
import com.j2km.inmueblesgo.service.PisoRepository;
import com.j2km.inmueblesgo.service.PisoService;
import com.j2km.inmueblesgo.service.TipoInmuebleRepository;
import com.j2km.inmueblesgo.service.TipoInmuebleService;
import com.j2km.inmueblesgo.service.TipoPlantaDetalleRepository;
import com.j2km.inmueblesgo.service.TipoPlantaRepository;
import com.j2km.inmueblesgo.service.TipoPlantaService;
import com.j2km.inmueblesgo.service.TorreRepository;
import com.j2km.inmueblesgo.service.TorreService;
import com.j2km.inmueblesgo.web.util.MessageFactory;

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

@Named("torreBusinessBean")
@ViewScoped
public class TorreBusinessBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(TorreBusinessBean.class.getName());

    private TorreEntity torre;
    private TipoPlantaEntity tipoPlanta;
    private int pisoInicial;
    private int pisoFinal;

    private List<TipoInmuebleEntity> tipoInmuebleList;
    private List<TipoPlantaEntity> tipoPlantaList;

    @Inject
    private TorreRepository torreService;

    @Inject
    private InmuebleRepository inmuebleService;
    
    @Inject
    private TipoPlantaDetalleRepository tipoPlantaDetalleService;

    @Inject
    private TipoInmuebleRepository tipoInmuebleService;

    @Inject
    private TipoPlantaRepository tipoPlantaService;

    @Inject
    private EstadoInmuebleRepository estadoInmuebleService;

    @Inject
    private PisoRepository pisoService;

    public TorreEntity getTorre() {
        return torre;
    }

    public void setTorre(TorreEntity torre) {
        this.torre = torre;
    }

    public List<TipoInmuebleEntity> getTipoInmuebleList() {
        if (tipoInmuebleList == null) {
            tipoInmuebleList = tipoInmuebleService.findAll();
        }
        return tipoInmuebleList;
    }

    public void setTipoInmuebleList(List<TipoInmuebleEntity> tipoInmuebleList) {
        this.tipoInmuebleList = tipoInmuebleList;
    }

    public List<TipoPlantaEntity> getTipoPlantaList() {
        if (tipoPlantaList == null) {
            tipoPlantaList = tipoPlantaService.findAll();
        }
        return tipoPlantaList;
    }

    public void setTipoPlantaList(List<TipoPlantaEntity> tipoPlantaList) {
        this.tipoPlantaList = tipoPlantaList;
    }

    public TipoPlantaEntity getTipoPlanta() {
        return tipoPlanta;
    }

    public void setTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }

    public int getPisoInicial() {
        return pisoInicial;
    }

    public void setPisoInicial(int pisoInicial) {
        this.pisoInicial = pisoInicial;
    }

    public int getPisoFinal() {
        return pisoFinal;
    }

    public void setPisoFinal(int pisoFinal) {
        this.pisoFinal = pisoFinal;
    }

    public void generarMasivo() {

        InmuebleEntity inmueble;
        List<TipoPlantaDetalleEntity> tipoPlantaDetalle = tipoPlantaDetalleService.findByTipoPlanta(tipoPlanta);

        for (int i = pisoInicial; i <= pisoFinal; i++) {
            //System.out.println(i);
            //System.out.println(tipoPlanta);
            PisoEntity p = pisoService.findOptionalByNumeroAndTorre(i, torre);

            if (p == null) {
                p = new PisoEntity();
                p.setNumero(i);
                p.setTipoPlanta(tipoPlanta);
                p.setTorre(torre);
                pisoService.save(p);

                for (TipoPlantaDetalleEntity detalle : tipoPlantaDetalle) {

                    inmueble = new InmuebleEntity();
                    inmueble.setArea(detalle.getTipoInmueble().getArea());
                    inmueble.setValorMetroCuadrado(detalle.getTipoInmueble().getValorMetroCuadrado());
                    inmueble.setProyecto(torre.getProyecto());
                    inmueble.setValorSeparacion(detalle.getTipoInmueble().getValorSeparacion());
                    inmueble.setEstadoInmueble(estadoInmuebleService.findOptionalByCodigo("01"));
                    inmueble.setNumero(i + " " + detalle.getNumero());
                    inmueble.setValorTotal(
                            detalle.getTipoInmueble().getValorMetroCuadrado() * detalle.getTipoInmueble().getArea());
                    inmueble.setPiso(p);
                    inmuebleService.save(inmueble);

                    System.out.println(detalle.getTipoInmueble());
                }
            }
        }

        FacesMessage facesMsg = new FacesMessage(
                FacesMessage.SEVERITY_INFO,
                "Pisos generados",
                "Pisos generados");
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        pisoListTorre = pisoService.findByTorre(this.torre);
    }

    private List<PisoEntity> pisoListTorre;

    public List<PisoEntity> getPisoListTorre() {
        return pisoListTorre;
    }

    public void setPisoListTorre(List<PisoEntity> pisoListTorre) {
        this.pisoListTorre = pisoListTorre;
    }

    public void onLoad() {
        this.pisoListTorre = pisoService.findByTorre(this.torre);

    }

    public void guardarDetalle() {

    }
}
