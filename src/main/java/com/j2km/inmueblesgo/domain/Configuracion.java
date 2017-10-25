package com.j2km.inmueblesgo.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author jkelsy
 */
@Entity(name = "Configuracion")
@Table(name = "configuracion")
public class Configuracion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    private String urlReporte;
    
    @Basic
    private String usuarioReporte;
    
    @Basic
    private String passwordReporte;

    public String getUsuarioReporte() {
        return usuarioReporte;
    }

    public void setUsuarioReporte(String usuarioReporte) {
        this.usuarioReporte = usuarioReporte;
    }

    public String getPasswordReporte() {
        return passwordReporte;
    }

    public void setPasswordReporte(String passwordReporte) {
        this.passwordReporte = passwordReporte;
    }

    public String getUrlReporte() {
        return urlReporte;
    }

    public void setUrlReporte(String urlReporte) {
        this.urlReporte = urlReporte;
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
        final Configuracion other = (Configuracion) obj;
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
        return "Configuracion{" + " id=" + id + '}';
    }
}
