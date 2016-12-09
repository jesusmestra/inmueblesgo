package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.Piso;
import com.j2km.inmueblesgo.domain.TorreEntity;
import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;


@Named
public class PisoService extends BaseService<Piso> implements Serializable {

    private static final long serialVersionUID = 1L;

    public PisoService() {
        super(Piso.class);
    }

    @Transactional
    public List<Piso> findAllTorreEntities() {

        return entityManager.createQuery("SELECT o FROM Piso o ", Piso.class).getResultList();
    }
  
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Piso o", Long.class).getSingleResult();
    }

    
    
    @Transactional
    public List<Piso> findAllByTorre(TorreEntity torre) {
        return entityManager.createQuery("SELECT o FROM Piso o WHERE o.torre = :torre", Piso.class)
                .setParameter("torre", torre)
                .getResultList();
    }
    
     @Transactional
    public Piso findByNumeroAndTorre(String n, TorreEntity torre) {
        
        
        List<Piso> lista = entityManager.createQuery("SELECT o FROM Piso o WHERE o.torre = :torre and o.numero = :numero", Piso.class)
                .setParameter("torre", torre)
                .setParameter("numero", n)
                .getResultList();

        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);

    }
    
    
}
