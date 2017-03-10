/**
 * This file was generated by the JPA Modeler
 */
package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jdmp
 */
@Entity(name = "EstadoProyecto")
@Table(name = "estado_proyecto")
public class EstadoProyectoEntity extends BaseEntity implements Serializable {

    @Column(name = "ep_codigo")
    @Basic
    private String codigo;

    @Column(name = "ep_nombre")
    @Basic
    private String nombre;

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}