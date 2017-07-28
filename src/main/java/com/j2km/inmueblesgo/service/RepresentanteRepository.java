package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.RepresentanteEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class RepresentanteRepository implements 
        EntityRepository<RepresentanteEntity, Long> , 
        CriteriaSupport<RepresentanteEntity> 
{
    public abstract List<RepresentanteEntity> findByEmpresa(EmpresaEntity empresa);
}