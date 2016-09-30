package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.TipoPlantaDetalleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class TipoPlantaService extends BaseService<TipoPlantaEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public TipoPlantaService() {
        super(TipoPlantaEntity.class);
    }

    @Transactional
    public List<TipoPlantaEntity> findAllTipoPlantaEntities() {

        return entityManager.createQuery("SELECT o FROM TipoPlanta o ", TipoPlantaEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM TipoPlanta o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(TipoPlantaEntity tercero) {

        /* This is called before a TipoPlanta is deleted. Place here all the
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
    public List<TipoPlantaEntity> findAvailableTipoPlantas(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM TipoPlanta o WHERE o.tipoIdentificacion IS NULL", TipoPlantaEntity.class).getResultList();
    }

    @Transactional
    public List<TipoPlantaEntity> findTipoPlantasByTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM TipoPlanta o WHERE o.tipoIdentificacion = :tipoIdentificacion", TipoPlantaEntity.class).setParameter("tipoIdentificacion", tipoIdentificacion).getResultList();
    }

    */

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<TipoPlantaEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM TipoPlanta o");

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

                    case "descripcion":
                        query.append(nextConnective).append(" o.descripcion LIKE :descripcion");
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

        System.out.println("MI FILTRO...." + query.toString());

        TypedQuery<TipoPlantaEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public List<TipoPlantaDetalleEntity> findAllDetallesByTipoPlanta(TipoPlantaEntity tipoPlantaEntity) {

        return entityManager.createQuery("SELECT o FROM TipoPlantaDetalle o where o.tipoPlanta = :tipoPlanta ", TipoPlantaDetalleEntity.class).
                setParameter("tipoPlanta", tipoPlantaEntity).
                getResultList();
    }

}
