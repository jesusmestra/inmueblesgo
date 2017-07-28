package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NotificacionVendedorEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class NotificacionVendedorRepository implements EntityRepository<NotificacionVendedorEntity, Long> , CriteriaSupport<NotificacionVendedorEntity> {
    public abstract List<NotificacionVendedorEntity> findByVendedor(UsuarioEntity vendedor);
    public abstract List<NotificacionVendedorEntity> findByCliente(TerceroEntity cliente);
    public abstract List<NotificacionVendedorEntity> findByVendedorAndCliente(UsuarioEntity vendedor, TerceroEntity cliente);
    
}