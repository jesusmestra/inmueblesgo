package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionObservacion;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class NegociacionObservacionRepository 
        extends AbstractEntityRepository<NegociacionObservacion, Long> 
        implements EntityRepository<NegociacionObservacion, Long> , 
                   CriteriaSupport<NegociacionObservacion> 
{   
    public abstract List<NegociacionObservacion> findByNegociacion(NegociacionEntity negociacion);
    
}