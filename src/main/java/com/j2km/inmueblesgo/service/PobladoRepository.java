package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class PobladoRepository implements EntityRepository<PobladoEntity, Long> , CriteriaSupport<PobladoEntity> {
    public abstract List<PobladoEntity> findByMunicipio(MunicipioEntity municipio);
}