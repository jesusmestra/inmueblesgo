package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table; 

@Entity(name = "NegociacionObservacion")
@Table(name = "negociacion_observacion")
public class NegociacionObservacion implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Basic
    @Column(name = "nob_observacion")
    private String observacion;
    
    @Column(name = "nob_fecha")
    @Basic
    private Date fecha;

    @ManyToOne(targetEntity = NegociacionEntity.class)
    @JoinColumn(name = "NEGOCIACION_ID")
    private NegociacionEntity negociacion;
    
    @ManyToOne(targetEntity = UsuarioEntity.class)
    @JoinColumn(name = "USUARIO_ID")
    private UsuarioEntity usuario;    

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public NegociacionEntity getNegociacion() {
        return this.negociacion;
    }

    public void setNegociacion(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
    }
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {return false;}
        final NegociacionObservacion other = (NegociacionObservacion) obj;
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
        return "NegociacionObservacion{" + " id=" + id + '}';
    }

}
