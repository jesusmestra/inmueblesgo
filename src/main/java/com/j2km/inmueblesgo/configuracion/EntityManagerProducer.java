package com.j2km.inmueblesgo.configuracion;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Producer for injectable EntityManager
 *
 */
public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "InmueblesDS")
    private EntityManager em;
}