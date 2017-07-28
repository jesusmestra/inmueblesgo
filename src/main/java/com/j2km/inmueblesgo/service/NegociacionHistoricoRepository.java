package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionHistoricoEntity;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class NegociacionHistoricoRepository implements EntityRepository<NegociacionHistoricoEntity, Long> , CriteriaSupport<NegociacionHistoricoEntity> {
    
    public abstract NegociacionHistoricoEntity findByNegociacionAndEstado(
            NegociacionEntity negociacion, 
            EstadoNegociacionEntity estado
    );
}