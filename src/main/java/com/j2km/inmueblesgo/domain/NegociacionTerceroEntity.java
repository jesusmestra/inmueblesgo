package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table; 

@Entity(name = "NegociacionTercero")
@Table(name = "negociacion_tercero")
public class NegociacionTerceroEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = NegociacionEntity.class)
    @JoinColumn(name = "NEGOCIACION_ID")
    private NegociacionEntity negociacion;

    @ManyToOne(targetEntity = TerceroEntity.class)
    @JoinColumn(name = "TERCERO_ID")
    private TerceroEntity tercero;

    public NegociacionEntity getNegociacion() {
        return this.negociacion;
    }

    public void setNegociacion(NegociacionEntity negociacion) {
        this.negociacion = negociacion;
    }

    public TerceroEntity getTercero() {
        return this.tercero;
    }

    public void setTercero(TerceroEntity tercero) {
        this.tercero = tercero;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {return false;}
        final NegociacionTerceroEntity other = (NegociacionTerceroEntity) obj;
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
        return "NegociacionTerceroEntity{" + " id=" + id + '}';
    }

}
