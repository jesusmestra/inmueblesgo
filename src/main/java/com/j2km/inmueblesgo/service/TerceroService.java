package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.TerceroEntity;
import com.j2km.inmueblesgo.domain.TipoIdentificacionEntity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class TerceroService extends BaseService<TerceroEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TerceroService() {
        super(TerceroEntity.class);
    }

    @Transactional
    public List<TerceroEntity> findAllTerceroEntities() {

        return entityManager.createQuery("SELECT o FROM Tercero o ", TerceroEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Tercero o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(TerceroEntity tercero) {

        /* This is called before a Tercero is deleted. Place here all the
           steps to cut dependencies to other entities */
    }

    @Transactional
    public List<TerceroEntity> findAvailableTerceros(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM Tercero o WHERE o.tipoIdentificacion IS NULL", TerceroEntity.class).getResultList();
    }

    @Transactional
    public List<TerceroEntity> findTercerosByTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM Tercero o WHERE o.tipoIdentificacion = :tipoIdentificacion", TerceroEntity.class).setParameter("tipoIdentificacion", tipoIdentificacion).getResultList();
    }

    @Transactional
    public TerceroEntity findByIdentificacion(String identificacion) {

        List<TerceroEntity> lista = entityManager.createQuery("SELECT o FROM Tercero o WHERE o.identificacion = :identificacion", TerceroEntity.class).setParameter("identificacion", identificacion).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<TerceroEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Tercero o");

        // Can be optimized: We need this join only when tipoIdentificacion filter is set
        query.append(" LEFT OUTER JOIN o.tipoIdentificacion tipoIdentificacion");

        String nextConnective = " WHERE";

        Map<String, Object> queryParameters = new HashMap<>();

        if (filters != null && !filters.isEmpty()) {

            nextConnective += " ( ";

            for (String filterProperty : filters.keySet()) {

                if (filters.get(filterProperty) == null) {
                    continue;
                }

                switch (filterProperty) {

                    case "identificacion":
                        query.append(nextConnective).append(" o.identificacion LIKE :identificacion");
                        queryParameters.put("identificacion", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "nombres":
                        query.append(nextConnective).append(" o.nombres LIKE :nombres");
                        queryParameters.put("nombres", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "apellido1":
                        query.append(nextConnective).append(" o.apellido1 LIKE :apellido1");
                        queryParameters.put("apellido1", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "apellido2":
                        query.append(nextConnective).append(" o.apellido2 LIKE :apellido2");
                        queryParameters.put("apellido2", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "tipoIdentificacion":
                        query.append(nextConnective).append(" o.tipoIdentificacion = :tipoIdentificacion");
                        queryParameters.put("tipoIdentificacion", filters.get(filterProperty));
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

        TypedQuery<TerceroEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
