package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.UsuarioEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class UsuarioService extends BaseService<UsuarioEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public UsuarioService() {
        super(UsuarioEntity.class);
    }

    @Transactional
    public List<UsuarioEntity> findAllUsuarioEntities() {

        return entityManager.createQuery("SELECT o FROM Usuario o ", UsuarioEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Usuario o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(UsuarioEntity usuario) {

        /* This is called before a Usuario is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        // 
    }  
    
    @Transactional
    public UsuarioEntity findByLogin(String login)  {
       
        List<UsuarioEntity> lista = entityManager.createQuery("FROM Usuario u WHERE u.login = :login", UsuarioEntity.class)
            .setParameter("login", login).getResultList();

        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

}
