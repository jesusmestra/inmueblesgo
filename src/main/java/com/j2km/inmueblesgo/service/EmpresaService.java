package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.PobladoEntity;
import com.j2km.inmueblesgo.domain.TerceroEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class EmpresaService extends BaseService<EmpresaEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public EmpresaService() {
        super(EmpresaEntity.class);
    }

    @Transactional
    public List<EmpresaEntity> findAllEmpresaEntities() {

        return entityManager.createQuery("SELECT o FROM Empresa o ", EmpresaEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Empresa o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(EmpresaEntity empresa) {

        /* This is called before a Empresa is deleted. Place here all the
           steps to cut dependencies to other entities */
    }

    @Transactional
    public List<EmpresaEntity> findAvailableEmpresas(PobladoEntity poblado) {
        return entityManager.createQuery("SELECT o FROM Empresa o WHERE o.poblado IS NULL", EmpresaEntity.class).getResultList();
    }

    @Transactional
    public List<EmpresaEntity> findEmpresasByPoblado(PobladoEntity poblado) {
        return entityManager.createQuery("SELECT o FROM Empresa o WHERE o.poblado = :poblado", EmpresaEntity.class).setParameter("poblado", poblado).getResultList();
    }

    @Transactional
    public List<EmpresaEntity> findEmpresasByRepresentante(TerceroEntity representante) {
        return entityManager.createQuery("SELECT o FROM Empresa o WHERE o.representante = :representante", EmpresaEntity.class).setParameter("representante", representante).getResultList();
    }

    @Transactional
    public EmpresaEntity findByNit(String nit) {
        List<EmpresaEntity> lista = entityManager.createQuery("SELECT o FROM Empresa o WHERE o.nit = :nit", EmpresaEntity.class).setParameter("nit", nit).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<EmpresaEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Empresa o");

        // Can be optimized: We need this join only when poblado filter is set
        query.append(" LEFT OUTER JOIN o.poblado poblado");

        query.append(" LEFT OUTER JOIN o.representante representante");

        String nextConnective = " WHERE";

        Map<String, Object> queryParameters = new HashMap<>();

        if (filters != null && !filters.isEmpty()) {

            nextConnective += " ( ";

            for (String filterProperty : filters.keySet()) {

                if (filters.get(filterProperty) == null) {
                    continue;
                }

                switch (filterProperty) {

                    case "nombre":
                        query.append(nextConnective).append(" o.nombre LIKE :nombre");
                        queryParameters.put("nombre", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "nit":
                        query.append(nextConnective).append(" o.nit LIKE :nit");
                        queryParameters.put("nit", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "poblado":
                        query.append(nextConnective).append(" o.poblado = :poblado");
                        queryParameters.put("poblado", filters.get(filterProperty));
                        break;

                    case "representante":
                        query.append(nextConnective).append(" o.representante = :representante");
                        queryParameters.put("representante", filters.get(filterProperty));
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


        TypedQuery<EmpresaEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
