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
import javax.persistence.Table;

/**
 * @author jdmp
 */
@Entity(name = "Oferta")
@Table(name = "oferta")
public class OfertaEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ofer_porcentaje")
    @Basic
    private Double porcentaje;

    @Column(name = "ofer_valor_separacion")
    @Basic
    private Double valorSeparacion;

    @Column(name = "ofer_nombre", unique = true)
    @Basic
    private String nombre;

    @Column(name = "ofer_numero_cuotas")
    @Basic
    private Integer numeroCuotas;

    @Column(name = "ofer_periodicidad")
    @Basic
    private String periodicidad;

    public Double getPorcentaje() {
        return this.porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Double getValorSeparacion() {
        return this.valorSeparacion;
    }

    public void setValorSeparacion(Double valorSeparacion) {
        this.valorSeparacion = valorSeparacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumeroCuotas() {
        return this.numeroCuotas;
    }

    public void setNumeroCuotas(Integer numeroCuotas) {
        this.numeroCuotas = numeroCuotas;
    }

    public String getPeriodicidad() {
        return this.periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {return false;}
        final OfertaEntity other = (OfertaEntity) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {        return false;        }
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
        return "OfertaEntity{" + " id=" + id + '}';
    }

}
