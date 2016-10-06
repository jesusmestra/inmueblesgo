/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.Rol;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jkelsy
 */
@Stateless
public class RolFacade extends AbstractFacade<Rol> {

    @PersistenceContext(unitName = "InmueblesDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }
    
    
    public Rol findByNombre(String nombre){
        List<Rol> roles = null;
        
        //try {
            String jpql = " FROM Rol r WHERE r.nombre = ?1";
            roles =  em.createQuery(jpql, Rol.class)
                    .setParameter(1, nombre).getResultList();
        /*} catch (Exception e) {
             System.err.println("ERRROROROORO"+e.getMessage());
            throw e;
        }*/
       if (roles == null || roles.isEmpty()) {
        return null;
    }

    return roles.get(0);
    }    
    
}
