package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.TipoPropiedad;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class TipoPropiedadRepository implements EntityRepository<TipoPropiedad, Long> , CriteriaSupport<TipoPropiedad> {
   public abstract TipoPropiedad findOptionalByDescripcion(String descripcion);
}