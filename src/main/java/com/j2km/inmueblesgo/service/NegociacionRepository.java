package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class NegociacionRepository implements EntityRepository<NegociacionEntity, Long> , CriteriaSupport<NegociacionEntity> {
    
    /*@Transactional
    public List<NegociacionEntity> findAllNuevas() {
        List<NegociacionEntity> lista = entityManager.createQuery("SELECT o FROM Negociacion o WHERE o.estadoNegociacion.nombre = 'Radicado'", NegociacionEntity.class).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }
        return lista;
    }*/ 
    
    public abstract List<NegociacionEntity> findByEstadoNegociacion_nombre(String nombre);
    public abstract NegociacionEntity findOptionalByInmueble(InmuebleEntity inmueble);
}