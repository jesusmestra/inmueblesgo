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

@Entity(name = "TipoInmueble")
@Table(name = "tipo_inmueble")
public class TipoInmuebleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ti_descripcion")
    @Basic
    private String descripcion;

    @Column(name = "ti_area")
    @Basic
    private Double area;

    @Column(name = "ti_valor_metro_cuadrado")
    @Basic
    private Double valorMetroCuadrado;

    @Column(name = "ti_valor_separacion")
    @Basic
    private Double valorSeparacion;
    
    @Column(name = "ti_imagen")
    @Basic
    private String imagen;

    @ManyToOne(targetEntity = ProyectoEntity.class)
    @JoinColumn(name = "PROYECTO_ID")
    private ProyectoEntity proyecto;
    
    @ManyToOne(targetEntity = TipoPropiedad.class)
    @JoinColumn(name = "TIPO_PROPIEDAD_ID")
    private TipoPropiedad tipoPropiedad;
    
    @ManyToOne(targetEntity = Archivo.class)
    @JoinColumn(name = "ARCHIVO_ID")
    private Archivo archivo;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getArea() {
        return this.area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getValorMetroCuadrado() {
        return this.valorMetroCuadrado;
    }

    public void setValorMetroCuadrado(Double valorMetroCuadrado) {
        this.valorMetroCuadrado = valorMetroCuadrado;
    }

    public Double getValorSeparacion() {
        return this.valorSeparacion;
    }

    public void setValorSeparacion(Double valorSeparacion) {
        this.valorSeparacion = valorSeparacion;
    }

    public ProyectoEntity getProyecto() {
        return this.proyecto;
    }

    public void setProyecto(ProyectoEntity proyecto) {
        this.proyecto = proyecto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {return false;}
        final TipoInmuebleEntity other = (TipoInmuebleEntity) obj;
        if (!java.util.Objects.equals(this.getId(), other.getId())) {return false;        }
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
        return "TipoInmuebleEntity{" + " id=" + id + '}';
    }

}
