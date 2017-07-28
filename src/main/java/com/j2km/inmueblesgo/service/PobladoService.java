package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.MunicipioEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class PobladoService implements Serializable {

    /*private static final long serialVersionUID = 1L;

    public PobladoService() {
        super(PobladoEntity.class);
    }

    @Transactional
    public List<PobladoEntity> findAllPobladoEntities() {

        return entityManager.createQuery("SELECT o FROM Poblado o ", PobladoEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Poblado o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(PobladoEntity poblado) {

        /* This is called before a Poblado is deleted. Place here all the
           steps to cut dependencies to other entities */
    /*}

    @Transactional
    public List<PobladoEntity> findAvailablePoblados(MunicipioEntity municipio) {
        return entityManager.createQuery("SELECT o FROM Poblado o WHERE o.municipio IS NULL", PobladoEntity.class).getResultList();
    }

    @Transactional
    public List<PobladoEntity> findPobladosByMunicipio(MunicipioEntity municipio) {
        return entityManager.createQuery("SELECT o FROM Poblado o WHERE o.municipio = :municipio", PobladoEntity.class).setParameter("municipio", municipio).getResultList();
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<PobladoEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Poblado o");

        // Can be optimized: We need this join only when tipoIdentificacion filter is set
        query.append(" LEFT OUTER JOIN o.municipio municipio");

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


                    case "municipio":
                        query.append(nextConnective).append(" o.municipio = :municipio");
                        queryParameters.put("municipio", filters.get(filterProperty));
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

        TypedQuery<PobladoEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }*/

}
