package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class TorreRepository implements EntityRepository<TorreEntity, Long> , CriteriaSupport<TorreEntity> {
    public abstract List<TorreEntity> findByProyecto(ProyectoEntity proyecto);
}