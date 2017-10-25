package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.PisoEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class InmuebleRepository implements EntityRepository<InmuebleEntity, Long> , CriteriaSupport<InmuebleEntity> {
    public abstract List<InmuebleEntity> findByProyecto(ProyectoEntity proyecto);
    public abstract List<InmuebleEntity> findByProyectoAndEstadoInmueble(
            ProyectoEntity proyecto,
            EstadoInmuebleEntity estadoInmueble
    );
    
    public abstract List<InmuebleEntity> findByPiso(PisoEntity piso);
    public abstract InmuebleEntity findOptionalByPisoAndNumero(PisoEntity piso, String numero);
}