package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class NegociacionTerceroService implements Serializable {

    /*private static final long serialVersionUID = 1L;

    public NegociacionTerceroService() {
        super(NegociacionTerceroEntity.class);
    }

    @Transactional
    public List<NegociacionTerceroEntity> findAllNegociacionTerceroEntities() {

        return entityManager.createQuery("SELECT o FROM NegociacionTercero o ", NegociacionTerceroEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM NegociacionTercero o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(NegociacionTerceroEntity tercero) {

        /* This is called before a Negociacion is deleted. Place here all the
           steps to cut dependencies to other entities */
        //  SI SE VA A ELIMINAR MUNICIPIOS Y POBLADOS EN CASCADA this.cutAllTipoIdentificacionTercerosAssignments(tipoIdentificacion);
    /*}

   

    @Transactional
    public List<NegociacionTerceroEntity> findAllNegociacionTerceroByNegociacion(NegociacionEntity negociacion) {
        List<NegociacionTerceroEntity> lista = entityManager.createQuery("SELECT o FROM NegociacionTercero o WHERE o.negociacion = :negociacion", NegociacionTerceroEntity.class).setParameter("negociacion", negociacion).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista;
    }
    
    @Transactional
    public List<NegociacionTerceroEntity> findAllNegociacionTerceroByNegociacionAndTercero(NegociacionEntity negociacion, TerceroEntity tercero) {
        List<NegociacionTerceroEntity> lista = 
                    entityManager.createQuery("SELECT o FROM NegociacionTercero o WHERE o.negociacion = :negociacion and o.tercero = :tercero", 
                            NegociacionTerceroEntity.class).
                            setParameter("negociacion", negociacion).
                            setParameter("tercero", tercero).
                            getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista;
    }*/

}
