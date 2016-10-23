/**
 * This file was generated by the JPA Modeler
 */
package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author jkelsy
 */
@Entity(name = "NegociacionHistorico")
@Table(name = "negociacion_historico")
public class NegociacionHistoricoEntity extends BaseEntity implements Serializable {

    @Basic
    private Date fecha;

    @Basic
    private String observacion;

    @ManyToOne(targetEntity = NegociacionEntity.class)
    @JoinColumn(name = "NEGOCIACION_ID")
    private NegociacionEntity negociacion;

    @ManyToOne(targetEntity = EstadoNegociacionEntity.class)
    @JoinColumn(name = "ESTADO_INMUEBLE_ID")
    private EstadoNegociacionEntity estadoNegociacion;

    @ManyToOne(targetEntity = UsuarioEntity.class)
    @JoinColumn(name = "CREADO_POR_ID")
    private UsuarioEntity creadoPor;

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public NegociacionEntity getNegociacion() {
        return this.negociacion;
    }

    public void setNegociacion(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
    }

    public EstadoNegociacionEntity getEstadoNegociacion() {
        return this.estadoNegociacion;
    }

    public void setEstadoNegociacion(EstadoNegociacionEntity estadoNegociacion) {
        this.estadoNegociacion = estadoNegociacion;
    }

    public UsuarioEntity getCreadoPor() {
        return this.creadoPor;
    }

    public void setCreadoPor(UsuarioEntity creadoPor) {
        this.creadoPor = creadoPor;
    }

}