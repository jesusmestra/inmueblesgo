package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.Archivo;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class ArchivoRepository implements EntityRepository<Archivo, Long> , CriteriaSupport<Archivo> {
    
}