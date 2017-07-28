package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class PlanPagoRepository implements EntityRepository<PlanPagoEntity, Long> , CriteriaSupport<PlanPagoEntity> {
    public abstract List<PlanPagoEntity> findByNegociacion(NegociacionEntity negociacion);
}