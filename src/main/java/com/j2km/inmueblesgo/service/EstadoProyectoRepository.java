package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class EstadoProyectoRepository implements EntityRepository<EstadoProyectoEntity, Long> , CriteriaSupport<EstadoProyectoEntity> {
    public abstract EstadoProyectoEntity findOptionalByCodigo(String codigo);
    public abstract EstadoProyectoEntity findOptionalByNombre(String nombre);   
    
}