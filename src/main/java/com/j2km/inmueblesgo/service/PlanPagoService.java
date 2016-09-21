package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.PlanPagoEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class PlanPagoService extends BaseService<PlanPagoEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public PlanPagoService() {
        super(PlanPagoEntity.class);
    }

    @Transactional
    public List<PlanPagoEntity> findAllPlanPagoEntities() {

        return entityManager.createQuery("SELECT o FROM PlanPago o ", PlanPagoEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM PlanPago o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(PlanPagoEntity municipio) {

        /* This is called before a Municipio is deleted. Place here all the
           steps to cut dependencies to other entities */
    }

    @Transactional
    public List<PlanPagoEntity> findAvailablePlanPago(NegociacionEntity negociacion) {
        return entityManager.createQuery("SELECT o FROM PlanPago o WHERE o.negociacion IS NULL", PlanPagoEntity.class).getResultList();
    }

    @Transactional
    public List<PlanPagoEntity> findPlanPagoByNegociacion(NegociacionEntity negociacion) {
        return entityManager.createQuery("SELECT o FROM PlanPago o WHERE o.negociacion = :negociacion", PlanPagoEntity.class).setParameter("negociacion", negociacion).getResultList();
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<PlanPagoEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM PlanPago o");

        // Can be optimized: We need this join only when tipoIdentificacion filter is set
        query.append(" LEFT OUTER JOIN o.negociacion negociacion");

        String nextConnective = " WHERE";

        Map<String, Object> queryParameters = new HashMap<>();

        if (filters != null && !filters.isEmpty()) {

            nextConnective += " ( ";

            for (String filterProperty : filters.keySet()) {

                if (filters.get(filterProperty) == null) {
                    continue;
                }

                switch (filterProperty) {

                    case "negociacion":
                        query.append(nextConnective).append(" o.negociacion = :negociacion");
                        queryParameters.put("negociacion", filters.get(filterProperty));
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

        TypedQuery<PlanPagoEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
