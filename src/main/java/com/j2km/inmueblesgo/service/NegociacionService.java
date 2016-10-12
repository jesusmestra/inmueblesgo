package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.InmuebleEntity;
import com.j2km.inmueblesgo.domain.NegociacionEntity;
import com.j2km.inmueblesgo.domain.NegociacionTerceroEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
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
public class NegociacionService extends BaseService<NegociacionEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public NegociacionService() {
        super(NegociacionEntity.class);
    }

    @Transactional
    public List<NegociacionEntity> findAllNegociacionEntities() {

        return entityManager.createQuery("SELECT o FROM Negociacion o ", NegociacionEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Negociacion o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(NegociacionEntity tercero) {

        /* This is called before a Negociacion is deleted. Place here all the
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
    public List<NegociacionEntity> findAvailableNegociacions(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM Negociacion o WHERE o.tipoIdentificacion IS NULL", NegociacionEntity.class).getResultList();
    }

    @Transactional
    public List<NegociacionEntity> findNegociacionsByTipoIdentificacion(TipoIdentificacionEntity tipoIdentificacion) {
        return entityManager.createQuery("SELECT o FROM Negociacion o WHERE o.tipoIdentificacion = :tipoIdentificacion", NegociacionEntity.class).setParameter("tipoIdentificacion", tipoIdentificacion).getResultList();
    }

     */
    @Transactional
    public NegociacionEntity findByInmueble(InmuebleEntity inmueble) {
        List<NegociacionEntity> lista = entityManager.createQuery("SELECT o FROM Negociacion o WHERE o.inmueble = :inmueble", NegociacionEntity.class).setParameter("inmueble", inmueble).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }

    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<NegociacionEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Negociacion o");

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

        TypedQuery<NegociacionEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    

}
