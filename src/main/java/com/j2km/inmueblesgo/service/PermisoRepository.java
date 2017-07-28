package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.RolEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class PermisoRepository implements EntityRepository<PermisoEntity, Long> , CriteriaSupport<PermisoEntity> {
    public abstract PermisoEntity findOptionalByUsuarioAndRol(
            UsuarioEntity usuario, 
            RolEntity rol
    );
}