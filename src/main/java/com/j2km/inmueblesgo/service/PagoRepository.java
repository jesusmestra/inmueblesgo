package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.Pago;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class PagoRepository extends AbstractEntityRepository<Pago, Long> 
        implements EntityRepository<Pago, Long> , CriteriaSupport<Pago> {
    
    public abstract List<Pago> findByNegociacion(NegociacionEntity negociacion); 
}