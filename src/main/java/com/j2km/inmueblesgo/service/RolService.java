package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.RolEntity;
import com.j2km.inmueblesgo.domain.RolEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class RolService extends BaseService<RolEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public RolService() {
        super(RolEntity.class);
    }

    @Transactional
    public List<RolEntity> findAllRolEntities() {
        return entityManager.createQuery("SELECT o FROM Rol o ", RolEntity.class).getResultList();
    }   

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Rol o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(RolEntity rol) {

        /* This is called before a Rol is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        // 
    }  
    
    @Transactional
    public RolEntity findByNombre(String nombre) {
        List<RolEntity> roles = entityManager.createQuery("SELECT o FROM Rol o where o.nombre = :nombre", RolEntity.class).
                setParameter("nombre", nombre).
                getResultList();
        if((roles == null) || (roles.isEmpty())){
            return null;
        }else{
            return roles.get(0);
        }
    }

}
