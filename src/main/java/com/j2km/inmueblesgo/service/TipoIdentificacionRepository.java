package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class TipoIdentificacionRepository implements EntityRepository<TipoIdentificacionEntity, Long> , CriteriaSupport<TipoIdentificacionEntity> {
    public abstract TipoIdentificacionEntity findOptionalByNombre(String nombre);
}