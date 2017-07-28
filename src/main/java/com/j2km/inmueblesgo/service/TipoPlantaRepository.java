package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class TipoPlantaRepository implements EntityRepository<TipoPlantaEntity, Long> , CriteriaSupport<TipoPlantaEntity> {
    public abstract List<TipoPlantaEntity> findByProyecto(ProyectoEntity proyecto);
}