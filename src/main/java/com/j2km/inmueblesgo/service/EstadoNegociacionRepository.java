package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class EstadoNegociacionRepository implements EntityRepository<EstadoNegociacionEntity, Long> , CriteriaSupport<EstadoNegociacionEntity> {
    public abstract EstadoNegociacionEntity findOptionalByNombre(String nombre);
}