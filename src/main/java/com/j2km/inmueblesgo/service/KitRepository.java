package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.Kit;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class KitRepository implements EntityRepository<Kit, Long> , CriteriaSupport<Kit> {
    public abstract List<Kit> findByProyecto(ProyectoEntity proyecto);
}