package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.TipoInmuebleEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class TipoInmuebleRepository implements EntityRepository<TipoInmuebleEntity, Long> , CriteriaSupport<TipoInmuebleEntity> {
    public abstract List<TipoInmuebleEntity> findByProyecto(ProyectoEntity proyecto);
}