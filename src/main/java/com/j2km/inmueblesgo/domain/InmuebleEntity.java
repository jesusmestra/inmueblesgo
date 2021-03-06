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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity(name = "Inmueble")
@Table(name = "inmueble")
@NamedQueries({
    @NamedQuery(name = "InmuebleEntity.findByProyecto", query = "Select i from Inmueble i WHERE i.proyecto.id = :proyecto_id"),
    @NamedQuery(name = "InmuebleEntity.findByProyectoAndEstado", query = "Select i from Inmueble i WHERE i.proyecto.id = :proyecto_id AND i.estadoInmueble.id = :estado_id")})
public class InmuebleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Version
    private int version;

    @Column(name = "inm_numero")
    @Basic
    private String numero;

    @Column(name = "inm_area")
    @Basic
    private Double area = 0d;

    @Column(name = "inm_valor_metro_cuadrado")
    @Basic
    private Double valorMetroCuadrado = 0d;

    @Column(name = "inm_incremento")
    @Basic
    private Double incremento = 0d;

    @Column(name = "inm_valor_separacion")
    @Basic
    private Double valorSeparacion = 0d;

    @ManyToOne(targetEntity = ProyectoEntity.class)
    @JoinColumn(name = "PROYECTO_ID")
    private ProyectoEntity proyecto;

    @ManyToOne(targetEntity = EstadoInmuebleEntity.class)
    @JoinColumn(name = "ESTADO_INMUEBLE_ID")
    private EstadoInmuebleEntity estadoInmueble;
    
    @ManyToOne(targetEntity = TipoInmuebleEntity.class)
    @JoinColumn(name = "TIPO_INMUEBLE_ID")
    private TipoInmuebleEntity tipoInmueble;

    @ManyToOne(targetEntity = PisoEntity.class)
    @JoinColumn(name = "PISO_ID")
    private PisoEntity piso;
    
    @ManyToOne(targetEntity = TipoPlantaEntity.class)
    @JoinColumn(name = "TIPO_PLANTA_ID")
    private TipoPlantaEntity tipoPlanta;
    
    @ManyToOne(targetEntity = TipoPropiedad.class)
    @JoinColumn(name = "TIPO_PROPIEDAD_ID")
    private TipoPropiedad tipoPropiedad;
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public TipoPlantaEntity getTipoPlanta() {
        return tipoPlanta;
    }

    public void setTipoPlanta(TipoPlantaEntity tipoPlanta) {
        this.tipoPlanta = tipoPlanta;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public Double getIncremento() {
        return this.incremento;
    }

    public void setIncremento(Double incremento) {
        this.incremento = incremento;
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

    public EstadoInmuebleEntity getEstadoInmueble() {
        return this.estadoInmueble;
    }

    public void setEstadoInmueble(EstadoInmuebleEntity estadoInmueble) {
        this.estadoInmueble = estadoInmueble;
    }

    public PisoEntity getPiso() {
        return this.piso;
    }

    public void setPiso(PisoEntity piso) {
        this.piso = piso;
    }

    public TipoInmuebleEntity getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmuebleEntity tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }    

    public TipoPropiedad getTipoPropiedad() {
        return tipoPropiedad;
    }

    public void setTipoPropiedad(TipoPropiedad tipoPropiedad) {
        this.tipoPropiedad = tipoPropiedad;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {return false;}
        if (!java.util.Objects.equals(getClass(), obj.getClass())) {return false;}
        final InmuebleEntity other = (InmuebleEntity) obj;
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
        return "InmuebleEntity{" + " id=" + id + '}';
    }
    
    public Double getValorTotal(){
        if(tipoPropiedad == null){
            return 0d;
        }
        Double tempo = 0d;
        if("APARTAMENTO".equals(tipoPropiedad.getDescripcion())){
            tempo = valorMetroCuadrado * area;
        }else if("CASA".equals(tipoPropiedad.getDescripcion())){
            tempo = valorMetroCuadrado;
        }
        
        if(incremento != null){
            tempo = tempo + incremento;
        }
        return tempo ;
    }

}
