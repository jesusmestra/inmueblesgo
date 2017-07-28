package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.UsuarioEntity;
import java.io.Serializable;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class UsuarioRepository extends AbstractEntityRepository<UsuarioEntity, Long> implements EntityRepository<UsuarioEntity, Long> , CriteriaSupport<UsuarioEntity>, Serializable {
    public abstract UsuarioEntity findOptionalByLogin(String login);
    
    public Boolean esVendedor(UsuarioEntity usuario){
        List<UsuarioEntity> lista = typedQuery(
                "Select e.usuario from Permiso e WHERE e.usuario = ?1 and e.rol.nombre = 'VENDEDOR'"
        ).setParameter(1, usuario).getResultList();
        
        return !(lista == null || lista.isEmpty());
    }
    
    public Boolean esAdmin(UsuarioEntity usuario){
        List<UsuarioEntity> lista = typedQuery(
                "Select e.usuario from Permiso e WHERE e.usuario = ?1 and e.rol.nombre = 'ADMIN'"
        ).setParameter(1, usuario).getResultList();
        
        return !(lista == null || lista.isEmpty());
    }
    
}