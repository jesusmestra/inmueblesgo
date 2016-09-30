/**
 * This file was generated by the JPA Modeler
 */
package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author jkelsy
 */
@Entity(name = "Piso")
@Table(name = "piso")
public class Piso extends BaseEntity implements Serializable {

    @Column(name = "piso_numero")
    @Basic
    private String numero;

    @ManyToOne(targetEntity = TorreEntity.class)
    @JoinColumn(name = "TORRE_ID")
    private TorreEntity torre;

    @ManyToOne(targetEntity = TipoPlantaEntity.class)
    @JoinColumn(name = "TIPO_PLANTA_ID")
    private TipoPlantaEntity tipoPlanta;

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TorreEntity getTorre() {
        return this.torre;
    }

    public void setTorre(TorreEntity torre) {
        this.torre = torre;
    }

    public TipoPlantaEntity getTipoPlanta() {
        return this.tipoPlanta;
    }

    public void setTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }

}
