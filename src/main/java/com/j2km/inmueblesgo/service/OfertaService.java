package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.OfertaEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class OfertaService extends BaseService<OfertaEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public OfertaService() {
        super(OfertaEntity.class);
    }

    @Transactional
    public List<OfertaEntity> findAllOfertaEntities() {

        return entityManager.createQuery("SELECT o FROM Oferta o ", OfertaEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Oferta o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(OfertaEntity tercero) {

        /* This is called before a Oferta is deleted. Place here all the
           steps to cut dependencies to other entities */
        //  SI SE VA A ELIMINAR MUNICIPIOS Y POBLADOS EN CASCADA this.cutAllTipoIdentificacionTercerosAssignments(tipoIdentificacion);
    }

    @Transactional
    public OfertaEntity findByNombre(String nombre) {

        List<OfertaEntity> lista = entityManager.createQuery("SELECT o FROM Oferta o WHERE o.nombre = :nombre", OfertaEntity.class).setParameter("nombre", nombre).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
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
    public List<OfertaEntity> findAvailableOfertas(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM Oferta o WHERE o.tipoIdentificacion IS NULL", OfertaEntity.class).getResultList();
    }

    @Transactional
    public List<OfertaEntity> findOfertasByTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM Oferta o WHERE o.tipoIdentificacion = :tipoIdentificacion", OfertaEntity.class).setParameter("tipoIdentificacion", tipoIdentificacion).getResultList();
    }

     */
    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<OfertaEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Oferta o");

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

                    case "porcentaje":
                        query.append(nextConnective).append(" o.porcentaje LIKE :porcentaje");
                        queryParameters.put("porcentaje", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "valorSeparacion":
                        query.append(nextConnective).append(" o.valorSeparacion LIKE :valorSeparacion");
                        queryParameters.put("valorSeparacion", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "nombre":
                        query.append(nextConnective).append(" o.nombre LIKE :nombre");
                        queryParameters.put("nombre", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "numeroCuotas":
                        query.append(nextConnective).append(" o.numeroCuotas LIKE :numeroCuotas");
                        queryParameters.put("numeroCuotas", "%" + filters.get(filterProperty) + "%");
                        break;

                    case "periodicidad":
                        query.append(nextConnective).append(" o.periodicidad LIKE :periodicidad");
                        queryParameters.put("periodicidad", "%" + filters.get(filterProperty) + "%");
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

        TypedQuery<OfertaEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

}
