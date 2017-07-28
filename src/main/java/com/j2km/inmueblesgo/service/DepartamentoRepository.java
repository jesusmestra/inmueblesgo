package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class DepartamentoRepository implements EntityRepository<DepartamentoEntity, Long> , CriteriaSupport<DepartamentoEntity> {
    
}