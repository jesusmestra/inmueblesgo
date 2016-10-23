package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.EstadoNegociacionEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class EstadoNegociacionService extends BaseService<EstadoNegociacionEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public EstadoNegociacionService(){
        super(EstadoNegociacionEntity.class);
    }
    
    @Transactional
    public List<EstadoNegociacionEntity> findAllEstadoNegociacionEntities() {
        
        return entityManager.createQuery("SELECT o FROM EstadoNegociacion o ", EstadoNegociacionEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM EstadoNegociacion o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(EstadoNegociacionEntity estadoInmueble) {

        /* This is called before a EstadoNegociacion is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        this.cutAllEstadoNegociacionTercerosAssignments(estadoInmueble);
        
    }

    // Remove all assignments from all tercero a estadoInmueble. Called before delete a estadoInmueble.
    @Transactional
    private void cutAllEstadoNegociacionTercerosAssignments(EstadoNegociacionEntity estadoInmueble) {
        entityManager
                .createQuery("UPDATE Tercero c SET c.estadoInmueble = NULL WHERE c.estadoInmueble = :p")
                .setParameter("p", estadoInmueble).executeUpdate();
    }
    
    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<EstadoNegociacionEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        
        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM EstadoNegociacion o");
        
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
        
        TypedQuery<EstadoNegociacionEntity> q = this.entityManager.createQuery(query.toString(), this.getType());
        
        for(String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public EstadoNegociacionEntity findByCodigo(String codigo) {
        List<EstadoNegociacionEntity> lista = entityManager.createQuery("SELECT o FROM EstadoNegociacion o WHERE o.codigo = :codigo", EstadoNegociacionEntity.class).setParameter("codigo", codigo).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }
    

    @Transactional
    public EstadoNegociacionEntity findByNombre(String nombre) {
        List<EstadoNegociacionEntity> lista = entityManager.createQuery("SELECT o FROM EstadoNegociacion o WHERE o.nombre = :nombre", EstadoNegociacionEntity.class).setParameter("nombre", nombre).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    
}
