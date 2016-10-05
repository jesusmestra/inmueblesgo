/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.Permiso;
import com.j2km.inmueblesgo.domain.Rol;
import com.j2km.inmueblesgo.domain.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jkelsy
 */
@Stateless
public class PermisoFacade extends AbstractFacade<Permiso> {

    @PersistenceContext(unitName = "InmueblesDS")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermisoFacade() {
        super(Permiso.class);
    }

    public Permiso findByUsuarioAndRol(Usuario usuario, Rol rol){
        List<Permiso> lista = null;

       // try {
            String jpql = " FROM Permiso p WHERE p.usuario.id = ?1 and p.rol.id = ?2";
            lista = em.createQuery(jpql, Permiso.class)
                    .setParameter(1, usuario.getId())
                    .setParameter(2, rol.getId())
                    .getResultList();
/*        } catch (Exception e) {
            System.err.println("ERRROROROORO" + e.getMessage());
            throw e;
        }*/
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

}
