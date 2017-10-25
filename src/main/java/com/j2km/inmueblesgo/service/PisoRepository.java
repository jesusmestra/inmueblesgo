package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.PisoEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class PisoRepository implements EntityRepository<PisoEntity, Long> , CriteriaSupport<PisoEntity> {
    public abstract PisoEntity findOptionalByNumeroAndTorre(
            Integer numero,
            TorreEntity torre
    );
    
    public abstract List<PisoEntity> findByTorre(TorreEntity torre);
}