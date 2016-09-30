package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.TipoInmuebleEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class TipoInmuebleService extends BaseService<TipoInmuebleEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TipoInmuebleService() {
        super(TipoInmuebleEntity.class);
    }

    @Transactional
    public List<TipoInmuebleEntity> findAllTipoInmuebleEntities() {

        return entityManager.createQuery("SELECT o FROM TipoInmueble o ", TipoInmuebleEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM TipoInmueble o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(TipoInmuebleEntity tercero) {

        /* This is called before a TipoInmueble is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        //  SI SE VA A ELIMINAR MUNICIPIOS Y POBLADOS EN CASCADA this.cutAllTipoIdentificacionTercerosAssignments(tipoIdentificacion);
    }
    
    /*
    SI SE VA A ELIMINAR MUNICIPIOS Y POBLADOS EN CASCADA    
    // Remove all assignments from all tercero a tipoIdentificacion. Called before delete a tipoIdentificacion.
    @Transactional
    private void cutAllTipoIdentificacionTercerosAssignments(TipoIdentificacionEntity tipoIdentificacion) {
        entityManager
                .createQuery("UPDATE Tercero c SET c.tipoIdentificacion = NULL WHERE c.tipoIdentificacion = :p")
                .setParameter("p", tipoIdentificacion).executeUpdate();
    }

*/
    

    /* PARA LAS BUSQUEDAS YA SEA POR MUNICIPIO
    
    @Transactional
    public List<TipoInmuebleEntity> findAvailableTipoInmuebles(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM TipoInmueble o WHERE o.tipoIdentificacion IS NULL", TipoInmuebleEntity.class).getResultList();
    }

    @Transactional
    public List<TipoInmuebleEntity> findTipoInmueblesByTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM TipoInmueble o WHERE o.tipoIdentificacion = :tipoIdentificacion", TipoInmuebleEntity.class).setParameter("tipoIdentificacion", tipoIdentificacion).getResultList();
    }

    */

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<TipoInmuebleEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM TipoInmueble o");

        // Can be optimized: We need this join only when tipoIdentificacion filter is set
        //query.append(" LEFT OUTER JOIN o.tipoIdentificacion tipoIdentificacion");

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

        TypedQuery<TipoInmuebleEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
