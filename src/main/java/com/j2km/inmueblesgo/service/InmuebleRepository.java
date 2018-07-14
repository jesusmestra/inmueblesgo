package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.PisoEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import java.util.List;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class InmuebleRepository extends AbstractEntityRepository<InmuebleEntity, Long> implements EntityRepository<InmuebleEntity, Long> , CriteriaSupport<InmuebleEntity> {
    public abstract List<InmuebleEntity> findByProyecto(ProyectoEntity proyecto);
    public abstract List<InmuebleEntity> findByProyectoAndEstadoInmueble(
            ProyectoEntity proyecto,
            EstadoInmuebleEntity estadoInmueble
    );
    public List<InmuebleEntity> findByTorreAndEstadoInmueble(
            TorreEntity torre,
            EstadoInmuebleEntity estadoInmueble){
        
        List<InmuebleEntity> lista = typedQuery(
                "Select e from Inmueble e WHERE e.piso.torre = ?1 and e.estadoInmueble = ?2")
         .setParameter(1, torre)
         .setParameter(2, estadoInmueble)
         .getResultList();
        
        return lista;
        
    }
    
    public List<InmuebleEntity> findByTorre(TorreEntity torre){        
        List<InmuebleEntity> lista = typedQuery(
                "Select e from Inmueble e WHERE e.piso.torre = ?1")
         .setParameter(1, torre)       
         .getResultList();   
        
        return lista;        
    }
    
    public abstract List<InmuebleEntity> findByPiso(PisoEntity piso);
    public abstract InmuebleEntity findOptionalByPisoAndNumero(PisoEntity piso, String numero);
}