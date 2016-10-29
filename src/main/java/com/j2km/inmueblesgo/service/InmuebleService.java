package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EstadoInmuebleEntity;
import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class InmuebleService extends BaseService<InmuebleEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public InmuebleService() {
        super(InmuebleEntity.class);
    }

    @Transactional
    public List<InmuebleEntity> findAllInmuebleEntities() {

        return entityManager.createQuery("SELECT o FROM Inmueble o ", InmuebleEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Inmueble o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(InmuebleEntity inmueble) {

        /* This is called before a Inmueble is deleted. Place here all the
           steps to cut dependencies to other entities */
    }

    @Transactional
    public List<InmuebleEntity> findAvailableInmuebles(EstadoInmuebleEntity estadoInmueble) {
        return entityManager.createQuery("SELECT o FROM Inmueble o WHERE o.estadoInmueble IS NULL", InmuebleEntity.class).getResultList();
    }

    @Transactional
    public List<InmuebleEntity> findInmueblesByEstadoInmueble(EstadoInmuebleEntity estadoInmueble) {
        return entityManager.createQuery("SELECT o FROM Inmueble o WHERE o.estadoInmueble = :estadoInmueble", InmuebleEntity.class).setParameter("estadoInmueble", estadoInmueble).getResultList();
    }


    @Transactional
    public List<InmuebleEntity> findAvailableProyecto(ProyectoEntity proyecto) {
        return entityManager.createQuery("SELECT o FROM Inmueble o WHERE o.proyecto IS NULL", InmuebleEntity.class).getResultList();
    }

    @Transactional
    public List<InmuebleEntity> findInmueblesByProyecto(ProyectoEntity proyecto) {
        return entityManager.createQuery("SELECT o FROM Inmueble o WHERE o.proyecto = :proyecto", InmuebleEntity.class).setParameter("proyecto", proyecto).getResultList();
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<InmuebleEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Inmueble o");

        // Can be optimized: We need this join only when tipoIdentificacion filter is set
        query.append(" LEFT OUTER JOIN o.estadoInmueble estadoInmueble");
        query.append(" LEFT OUTER JOIN o.proyecto proyecto");

        String nextConnective = " WHERE";

        Map<String, Object> queryParameters = new HashMap<>();

        if (filters != null && !filters.isEmpty()) {

            nextConnective += " ( ";

            for (String filterProperty : filters.keySet()) {

                if (filters.get(filterProperty) == null) {
                    continue;
                }

                switch (filterProperty) {

                    case "codigo":
                        query.append(nextConnective).append(" o.codigo LIKE :codigo");
                        queryParameters.put("codigo", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "nombre":
                        query.append(nextConnective).append(" o.nombre LIKE :nombre");
                        queryParameters.put("nombre", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "estadoInmueble":
                        query.append(nextConnective).append(" o.estadoInmueble = :estadoInmueble");
                        queryParameters.put("estadoInmueble", filters.get(filterProperty));
                        break;

                    case "proyecto":
                        query.append(nextConnective).append(" o.proyecto = :proyecto");
                        queryParameters.put("proyecto", filters.get(filterProperty));
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

        System.out.println("MI FILTRO...." + query.toString());

        TypedQuery<InmuebleEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    /****************************/
    
        @Transactional
    public List<InmuebleEntity> findAllInmueblesByProyectoAndEstado(ProyectoEntity proyecto, EstadoInmuebleEntity estadoInmueble) {
        return entityManager.createQuery("SELECT o FROM Inmueble o WHERE o.proyecto = :proyecto and o.estadoInmueble = :estadoInmueble", InmuebleEntity.class)
                .setParameter("proyecto", proyecto)
                .setParameter("estadoInmueble", estadoInmueble)
                .getResultList();
    }

    

}
