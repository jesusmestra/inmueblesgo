package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class EstadoInmuebleRepository implements EntityRepository<EstadoInmuebleEntity, Long> , CriteriaSupport<EstadoInmuebleEntity> {
    public abstract EstadoInmuebleEntity findOptionalByNombre(String nombre);
    public abstract EstadoInmuebleEntity findOptionalByCodigo(String codigo);
}