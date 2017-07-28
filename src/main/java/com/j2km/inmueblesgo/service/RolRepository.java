package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.RolEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class RolRepository implements EntityRepository<RolEntity, Long> , CriteriaSupport<RolEntity> {
    public abstract RolEntity findOptionalByNombre(String nombre);
}