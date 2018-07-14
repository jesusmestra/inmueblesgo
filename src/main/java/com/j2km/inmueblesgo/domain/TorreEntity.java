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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "Torre")
@Table(name = "torre")
public class TorreEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "torre_nombre")
    @Basic
    private String nombre;

    @Column(name = "torre_direccion")
    @Basic
    private String direccion;

    @Column(name = "torre_numero")
    @Basic
    private String numero;
    
    @Column(name = "torre_piso_inicial")
    @Basic
    private Integer pisoInicial;
    
    @Column(name = "torre_numero_pisos")
    @Basic
    private Integer numeroPisos;

    @ManyToOne(targetEntity = ProyectoEntity.class)
    private ProyectoEntity proyecto;
    
    @ManyToOne(targetEntity = TipoPropiedad.class)
    private TipoPropiedad tipoPropiedad;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

    	if (this == obj) {
    		return true;
    	} else if (obj == null) {
            return false;
        } else if (obj.getClass() != this.getClass()) {
            return false;
        }else {
            return false;
        }
    }

    @Override
    public String toString() {
    	return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }
    
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ProyectoEntity getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
    }

    public Integer getPisoInicial() {
        return pisoInicial;
    }

    public void setPisoInicial(Integer pisoInicial) {
        this.pisoInicial = pisoInicial;
    }

    public Integer getNumeroPisos() {
        return numeroPisos;
    }

    public void setNumeroPisos(Integer numeroPisos) {
        this.numeroPisos = numeroPisos;
    }

    public TipoPropiedad getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(TipoPropiedad tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }    

}
