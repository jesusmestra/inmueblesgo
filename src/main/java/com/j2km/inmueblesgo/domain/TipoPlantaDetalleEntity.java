/**
 * This file was generated by the JPA Modeler
 */
package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "TipoPlantaDetalle")
@Table(name = "tipo_planta_detalle")
public class TipoPlantaDetalleEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tpd_numero")
    @Basic
    private Integer numero;

    @ManyToOne(targetEntity = TipoPlantaEntity.class)
    @JoinColumn(name = "TIPO_PLANTA_ID")
    private TipoPlantaEntity tipoPlanta;

    @ManyToOne(targetEntity = TipoInmuebleEntity.class)
    @JoinColumn(name = "TIPO_INMUEBLE_ID")
    private TipoInmuebleEntity tipoInmueble;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {return false;}
        final TipoPlantaDetalleEntity other = (TipoPlantaDetalleEntity) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {return false;}
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "TipoPlantaDetalleEntity{" + " id=" + id + '}';
    }

}
