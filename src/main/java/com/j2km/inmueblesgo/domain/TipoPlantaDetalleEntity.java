/**
 * This file was generated by the JPA Modeler
 */
package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author jdmp
 */
@Entity(name = "TipoPlantaDetalle")
@Table(name = "tipo_planta_detalle")
public class TipoPlantaDetalleEntity extends BaseEntity implements Serializable {

    @Column(name = "tp_detalle_numero")
    @Basic
    private Integer numero;

    @ManyToOne(cascade = {CascadeType.ALL}, targetEntity = TipoPlantaEntity.class)
    @JoinColumn(name = "TIPO_PLANTA_ID")
    private TipoPlantaEntity tipoPlanta;

    @ManyToOne(cascade = {CascadeType.ALL}, targetEntity = TipoInmuebleEntity.class)
    @JoinColumn(name = "TIPO_INMUEBLE_ID")
    private TipoInmuebleEntity tipoInmueble;

    public Integer getNumero() {
        return this.numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public TipoPlantaEntity getTipoPlanta() {
        return this.tipoPlanta;
    }

    public void setTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }

    public TipoInmuebleEntity getTipoInmueble() {
        return this.tipoInmueble;
    }

    public void setTipoInmueble(TipoInmuebleEntity tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

}
