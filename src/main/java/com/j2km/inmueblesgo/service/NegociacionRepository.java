package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class NegociacionRepository implements EntityRepository<NegociacionEntity, Long> , CriteriaSupport<NegociacionEntity> {
    public abstract List<NegociacionEntity> findByEstadoNegociacion_nombre(String nombre);
    public abstract NegociacionEntity findOptionalByInmueble(InmuebleEntity inmueble);
    public abstract List<NegociacionEntity> findByVendedor(UsuarioEntity vendedor);
    public abstract List<NegociacionEntity> findByVendedorAndEstadoNegociacion_nombre(UsuarioEntity vendedor, String nombre);
    
}