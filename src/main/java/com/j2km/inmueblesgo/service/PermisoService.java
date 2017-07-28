package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.RolEntity;
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
public class PermisoService implements Serializable {

    /*private static final long serialVersionUID = 1L;

    public PermisoService() {
        super(PermisoEntity.class);
    }

    @Transactional
    public List<PermisoEntity> findAllPermisoEntities() {

        return entityManager.createQuery("SELECT o FROM Permiso o ", PermisoEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Permiso o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(PermisoEntity permiso) {

        /* This is called before a Permiso is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        // 
    /*} 
    
    @Transactional
    public PermisoEntity findByUsuarioAndRol(UsuarioEntity usuario, RolEntity rol){
        List<PermisoEntity> lista = null;
      
        String jpql = " FROM Permiso p WHERE p.usuario.id = ?1 and p.rol.id = ?2";
        lista = entityManager.createQuery(jpql, PermisoEntity.class)
                .setParameter(1, usuario.getId())
                .setParameter(2, rol.getId())
                .getResultList();

        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }*/

}
