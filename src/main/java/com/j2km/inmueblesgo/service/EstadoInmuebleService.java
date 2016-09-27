package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class EstadoInmuebleService extends BaseService<EstadoInmuebleEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public EstadoInmuebleService(){
        super(EstadoInmuebleEntity.class);
    }
    
    @Transactional
    public List<EstadoInmuebleEntity> findAllEstadoInmuebleEntities() {
        
        return entityManager.createQuery("SELECT o FROM EstadoInmueble o ", EstadoInmuebleEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM EstadoInmueble o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(EstadoInmuebleEntity estadoInmueble) {

        /* This is called before a EstadoInmueble is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        this.cutAllEstadoInmuebleTercerosAssignments(estadoInmueble);
        
    }

    // Remove all assignments from all tercero a estadoInmueble. Called before delete a estadoInmueble.
    @Transactional
    private void cutAllEstadoInmuebleTercerosAssignments(EstadoInmuebleEntity estadoInmueble) {
        entityManager
                .createQuery("UPDATE Tercero c SET c.estadoInmueble = NULL WHERE c.estadoInmueble = :p")
                .setParameter("p", estadoInmueble).executeUpdate();
    }
    
    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<EstadoInmuebleEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        
        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM EstadoInmueble o");
        
        String nextConnective = " WHERE";
        
        Map<String, Object> queryParameters = new HashMap<>();
        
        if (filters != null && !filters.isEmpty()) {
            
            nextConnective += " ( ";
            
            for(String filterProperty : filters.keySet()) {
                
                if (filters.get(filterProperty) == null) {
                    continue;
                }
                
                switch (filterProperty) {
                
                case "nombre":
                    query.append(nextConnective).append(" o.nombre LIKE :nombre");
                    queryParameters.put("nombre", "%" + filters.get(filterProperty) + "%");
                    break;

                case "codigo":
                    query.append(nextConnective).append(" o.codigo LIKE :codigo");
                    queryParameters.put("codigo", "%" + filters.get(filterProperty) + "%");
                    break;

                }
                
                nextConnective = " AND";
            }
            
            query.append(" ) ");
            nextConnective = " AND";
        }
        
        if (sortField != null && !sortField.isEmpty()) {
            query.append(" ORDER BY o.").append(sortField);
            query.append(SortOrder.DESCENDING.equals(sortOrder) ? " DESC" : " ASC");
        }
        
        TypedQuery<EstadoInmuebleEntity> q = this.entityManager.createQuery(query.toString(), this.getType());
        
        for(String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public EstadoInmuebleEntity findByCodigo(String codigo) {
        List<EstadoInmuebleEntity> lista = entityManager.createQuery("SELECT o FROM EstadoInmueble o WHERE o.codigo = :codigo", EstadoInmuebleEntity.class).setParameter("codigo", codigo).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }
    

    @Transactional
    public EstadoInmuebleEntity findByNombre(String nombre) {
        List<EstadoInmuebleEntity> lista = entityManager.createQuery("SELECT o FROM EstadoInmueble o WHERE o.nombre = :nombre", EstadoInmuebleEntity.class).setParameter("nombre", nombre).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    
}
