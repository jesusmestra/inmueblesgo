package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionKit;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class NegociacionKitRepository extends AbstractEntityRepository<NegociacionKit, Long> implements EntityRepository<NegociacionKit, Long> , CriteriaSupport<NegociacionKit> {
    
    public abstract List<NegociacionKit> findByNegociacion(NegociacionEntity negociacion);
    
}