package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class ProyectoRepository implements EntityRepository<ProyectoEntity, Long> , CriteriaSupport<ProyectoEntity> {
    public abstract List<ProyectoEntity> findByEstadoProyecto(EstadoProyectoEntity estado);
    public abstract List<ProyectoEntity> findByEmpresa(EmpresaEntity empresa);
    public abstract List<ProyectoEntity> findByEmpresaIsNull();
}