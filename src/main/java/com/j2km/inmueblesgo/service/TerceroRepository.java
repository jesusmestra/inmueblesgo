package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity_;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class TerceroRepository extends AbstractEntityRepository<TerceroEntity, Long> implements EntityRepository<TerceroEntity, Long> , CriteriaSupport<TerceroEntity> {
    
    public abstract TerceroEntity findOptionalByIdentificacion(String identificacion);
    public abstract List<TerceroEntity> findByUsuario(UsuarioEntity usuario);
    
    public List<TerceroEntity> findByNegociacion(NegociacionEntity negociacion){
        List<TerceroEntity> lista = typedQuery(
                "Select e.tercero from NegociacionTercero e WHERE e.negociacion = ?1"
        ).setParameter(1, negociacion).getResultList();
        return lista;
    }
    
    public List<TerceroEntity> buscar(TerceroEntity tercero){
        Criteria tempo = criteria();
        
        if((tercero.getIdentificacion() != null) && (!tercero.getIdentificacion().isEmpty())){
            tempo = tempo.eq(TerceroEntity_.identificacion, tercero.getIdentificacion());
        }
        
        if((tercero.getNombres()!= null) && (!tercero.getNombres().isEmpty())){
            tempo = tempo.likeIgnoreCase(TerceroEntity_.nombres, "%"+tercero.getNombres()+"%");
        }
        
        if((tercero.getApellido1()!= null) && (!tercero.getApellido1().isEmpty())){
            tempo = tempo.likeIgnoreCase(TerceroEntity_.apellido1, "%"+tercero.getApellido1()+"%");
        }
        
        if((tercero.getApellido2()!= null) && (!tercero.getApellido2().isEmpty())){
            tempo = tempo.likeIgnoreCase(TerceroEntity_.apellido2, "%"+tercero.getApellido2()+"%");
        }        
        
        return tempo.getResultList();
    }
    
}