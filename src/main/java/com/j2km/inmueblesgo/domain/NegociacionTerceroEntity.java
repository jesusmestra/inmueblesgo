/**
 * This file was generated by the JPA Modeler
 */
package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author jdmp
 */
@Entity(name = "NegociacionTercero")
@Table(name = "negociacion_tercero")
public class NegociacionTerceroEntity extends BaseEntity implements Serializable {

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

}
