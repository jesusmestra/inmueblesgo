package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class EstadoProyectoService extends BaseService<EstadoProyectoEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public EstadoProyectoService(){
        super(EstadoProyectoEntity.class);
    }
    
    @Transactional
    public List<EstadoProyectoEntity> findAllEstadoProyectoEntities() {
        
        return entityManager.createQuery("SELECT o FROM EstadoProyecto o ", EstadoProyectoEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM EstadoProyecto o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(EstadoProyectoEntity estadoProyecto) {

        /* This is called before a EstadoProyecto is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        this.cutAllEstadoProyectoTercerosAssignments(estadoProyecto);
        
    }

    // Remove all assignments from all tercero a estadoProyecto. Called before delete a estadoProyecto.
    @Transactional
    private void cutAllEstadoProyectoTercerosAssignments(EstadoProyectoEntity estadoProyecto) {
        entityManager
                .createQuery("UPDATE Tercero c SET c.estadoProyecto = NULL WHERE c.estadoProyecto = :p")
                .setParameter("p", estadoProyecto).executeUpdate();
    }
    
    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<EstadoProyectoEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        
        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM EstadoProyecto o");
        
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
        
        TypedQuery<EstadoProyectoEntity> q = this.entityManager.createQuery(query.toString(), this.getType());
        
        for(String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public EstadoProyectoEntity findByCodigo(String codigo) {
        List<EstadoProyectoEntity> lista = entityManager.createQuery("SELECT o FROM EstadoProyecto o WHERE o.codigo = :codigo", EstadoProyectoEntity.class).setParameter("codigo", codigo).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }
    

    @Transactional
    public EstadoProyectoEntity findByNombre(String nombre) {
        List<EstadoProyectoEntity> lista = entityManager.createQuery("SELECT o FROM EstadoProyecto o WHERE o.nombre = :nombre", EstadoProyectoEntity.class).setParameter("nombre", nombre).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    
}
