package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.TipoFuenteInformacionEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class TipoFuenteInformacionRepository implements EntityRepository<TipoFuenteInformacionEntity, Long> , CriteriaSupport<TipoFuenteInformacionEntity> {
    public abstract TipoFuenteInformacionEntity findOptionalByNombre(String nombre);
}