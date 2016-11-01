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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author jdmp
 */
@Entity(name = "Proyecto")
@Table(name = "proyecto")
@NamedQuery(name = "ProyectoEntity.findByEmpresa", query = "Select p from Proyecto p WHERE p.empresa.id = :empresa_id")
public class ProyectoEntity extends BaseEntity implements Serializable {

    @Column(name = "proyecto_nombre", unique = true)
    @Basic
    private String nombre;

    @Column(name = "proyecto_codigo")
    @Basic
    private String codigo;

    @Column(name = "proyecto_logo")
    @Basic
    private String logo;

    @Column(name = "proyecto_latitud")
    @Basic
    private String latitud;

    @Column(name = "proyecto_logitud")
    @Basic
    private String longitud;

    @ManyToOne(targetEntity = EmpresaEntity.class)
    @JoinColumn(name = "EMPRESA_ID")
    private EmpresaEntity empresa;

    @ManyToOne(targetEntity = OfertaEntity.class)
    @JoinColumn(name = "OFERTA_ID")
    private OfertaEntity oferta;

    @ManyToOne(targetEntity = PobladoEntity.class)
    @JoinColumn(name = "POBLADO_ID")
    private PobladoEntity poblado;

    @ManyToOne(targetEntity = EstadoProyectoEntity.class)
    @JoinColumn(name = "ESTADO_PROYECTO_ID")
    private EstadoProyectoEntity estadoProyecto;

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public EmpresaEntity getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(EmpresaEntity empresa) {
        this.empresa = empresa;
    }

    public OfertaEntity getOferta() {
        return this.oferta;
    }

    public void setOferta(OfertaEntity oferta) {
        this.oferta = oferta;
    }

    public PobladoEntity getPoblado() {
        return this.poblado;
    }

    public void setPoblado(PobladoEntity poblado) {
        this.poblado = poblado;
    }

    public EstadoProyectoEntity getEstadoProyecto() {
        return this.estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyectoEntity estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

}
