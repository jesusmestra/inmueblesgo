package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class MunicipioRepository implements EntityRepository<MunicipioEntity, Long> , CriteriaSupport<MunicipioEntity> {
    public abstract List<MunicipioEntity> findByDepartamento(DepartamentoEntity departamento);
}