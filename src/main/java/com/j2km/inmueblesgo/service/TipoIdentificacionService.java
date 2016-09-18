package com.j2km.inmueblesgo.service;

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
public class TipoIdentificacionService extends BaseService<TipoIdentificacionEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TipoIdentificacionService() {
        super(TipoIdentificacionEntity.class);
    }

    @Transactional
    public List<TipoIdentificacionEntity> findAllTipoIdentificacionEntities() {

        return entityManager.createQuery("SELECT o FROM TipoIdentificacion o ", TipoIdentificacionEntity.class).getResultList();
    }

    @Transactional
    public TipoIdentificacionEntity findByCodigo(String codigo) {

        List<TipoIdentificacionEntity> lista = entityManager.createQuery("SELECT o FROM TipoIdentificacion o WHERE o.codigo = :codigo", TipoIdentificacionEntity.class).setParameter("codigo", codigo).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);  
    }

    @Transactional
    public TipoIdentificacionEntity findByAbrebiatura(String abreviatura) {

        List<TipoIdentificacionEntity> lista = entityManager.createQuery("SELECT o FROM TipoIdentificacion o WHERE o.abreviatura = :abreviatura", TipoIdentificacionEntity.class).setParameter("abreviatura", abreviatura).getResultList();

        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM TipoIdentificacion o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(TipoIdentificacionEntity tipoIdentificacion) {

        /* This is called before a TipoIdentificacion is deleted. Place here all the
           steps to cut dependencies to other entities */
        this.cutAllTipoIdentificacionTercerosAssignments(tipoIdentificacion);

    }

    // Remove all assignments from all tercero a tipoIdentificacion. Called before delete a tipoIdentificacion.
    @Transactional
    private void cutAllTipoIdentificacionTercerosAssignments(TipoIdentificacionEntity tipoIdentificacion) {
        entityManager
                .createQuery("UPDATE Tercero c SET c.tipoIdentificacion = NULL WHERE c.tipoIdentificacion = :p")
                .setParameter("p", tipoIdentificacion).executeUpdate();
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<TipoIdentificacionEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM TipoIdentificacion o");

        String nextConnective = " WHERE";

        Map<String, Object> queryParameters = new HashMap<>();

        if (filters != null && !filters.isEmpty()) {

            nextConnective += " ( ";

            for (String filterProperty : filters.keySet()) {

                if (filters.get(filterProperty) == null) {
                    continue;
                }

                switch (filterProperty) {

                    case "descripcion":
                        query.append(nextConnective).append(" o.descripcion LIKE :descripcion");
                        queryParameters.put("descripcion", "%" + filters.get(filterProperty) + "%");
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

        TypedQuery<TipoIdentificacionEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
