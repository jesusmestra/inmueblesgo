package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class NegociacionTerceroRepository extends AbstractEntityRepository<NegociacionTerceroEntity, Long> implements EntityRepository<NegociacionTerceroEntity, Long> , CriteriaSupport<NegociacionTerceroEntity> {
    
    public abstract List<NegociacionTerceroEntity> findByNegociacion(NegociacionEntity negociacion);
    
    public abstract List<NegociacionTerceroEntity> findByTercero(TerceroEntity tercero);
    
    public abstract List<NegociacionTerceroEntity> findByNegociacionAndTercero(
            NegociacionEntity negociacion,
            TerceroEntity tercero
    );
    
}