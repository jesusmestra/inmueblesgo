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
import javax.persistence.Version;

/**
 * @author jkelsy
 */
@Entity(name = "NotificacionVendedor")
@Table(name = "notificacion_vendedor")
public class NotificacionVendedorEntity implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Version
    private int version;    

    @Column(name = "ntv_descripcion")
    @Basic
    private String descripcion;

    @Column(name = "ntv_fecha")
    @Basic
    private Date fecha;
    
    @Column(name = "ntv_activa")
    @Basic
    private Boolean activa;

    @ManyToOne(targetEntity = TerceroEntity.class)
    @JoinColumn(name = "CLIENTE_ID")
    private TerceroEntity cliente;

    @ManyToOne(targetEntity = UsuarioEntity.class)
    @JoinColumn(name = "VENDEDOR_ID")
    private UsuarioEntity vendedor;    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public TerceroEntity getCliente() {
        return cliente;
    }

    public void setCliente(TerceroEntity cliente) {
        this.cliente = cliente;
    }

    public UsuarioEntity getVendedor() {
        return vendedor;
    }

    public void setVendedor(UsuarioEntity vendedor) {
        this.vendedor = vendedor;
    }

}
