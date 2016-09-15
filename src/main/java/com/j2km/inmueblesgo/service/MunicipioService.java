package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.DepartamentoEntity;
import com.j2km.inmueblesgo.domain.MunicipioEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class MunicipioService extends BaseService<MunicipioEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public MunicipioService() {
        super(MunicipioEntity.class);
    }

    @Transactional
    public List<MunicipioEntity> findAllMunicipioEntities() {

        return entityManager.createQuery("SELECT o FROM Municipio o ", MunicipioEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Municipio o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(MunicipioEntity municipio) {

        /* This is called before a Municipio is deleted. Place here all the
           steps to cut dependencies to other entities */
    }

    @Transactional
    public List<MunicipioEntity> findAvailableMunicipios(DepartamentoEntity departamento) {
        return entityManager.createQuery("SELECT o FROM Municipio o WHERE o.departamento IS NULL", MunicipioEntity.class).getResultList();
    }

    @Transactional
    public List<MunicipioEntity> findMunicipiosByDepartamento(DepartamentoEntity departamento) {
        return entityManager.createQuery("SELECT o FROM Municipio o WHERE o.departamento = :departamento", MunicipioEntity.class).setParameter("departamento", departamento).getResultList();
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<MunicipioEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Municipio o");

        // Can be optimized: We need this join only when tipoIdentificacion filter is set
        query.append(" LEFT OUTER JOIN o.departamento departamento");

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


                    case "departamento":
                        query.append(nextConnective).append(" o.departamento = :departamento");
                        queryParameters.put("departamento", filters.get(filterProperty));
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

        TypedQuery<MunicipioEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
