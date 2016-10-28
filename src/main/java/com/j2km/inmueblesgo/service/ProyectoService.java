package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.EmpresaEntity;
import com.j2km.inmueblesgo.domain.EstadoProyectoEntity;
import com.j2km.inmueblesgo.domain.OfertaEntity;
import com.j2km.inmueblesgo.domain.ProyectoEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class ProyectoService extends BaseService<ProyectoEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public ProyectoService() {
        super(ProyectoEntity.class);
    }

    @Transactional
    public List<ProyectoEntity> findAllProyectoEntities() {

        return entityManager.createQuery("SELECT o FROM Proyecto o ", ProyectoEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Proyecto o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(ProyectoEntity proyecto) {

        /* This is called before a Proyecto is deleted. Place here all the
           steps to cut dependencies to other entities */
    }

    @Transactional
    public List<ProyectoEntity> findAvailableProyectos(OfertaEntity oferta) {
        return entityManager.createQuery("SELECT o FROM Proyecto o WHERE o.oferta IS NULL", ProyectoEntity.class).getResultList();
    }

    @Transactional
    public List<ProyectoEntity> findProyectosByOferta(OfertaEntity oferta) {
        return entityManager.createQuery("SELECT o FROM Proyecto o WHERE o.oferta = :oferta", ProyectoEntity.class).setParameter("oferta", oferta).getResultList();
    }

    
   @Transactional
    public List<ProyectoEntity> findAvailableEmpresa(EmpresaEntity empresa) {
        return entityManager.createQuery("SELECT o FROM Proyecto o WHERE o.empresa IS NULL", ProyectoEntity.class).getResultList();
    }

    @Transactional
    public List<ProyectoEntity> findProyectosByEmpresa(EmpresaEntity empresa) {
        return entityManager.createQuery("SELECT o FROM Proyecto o WHERE o.empresa = :empresa", ProyectoEntity.class).setParameter("empresa", empresa).getResultList();
    }    
    
    
    
    
@Transactional
    public ProyectoEntity findByCodigo(String codigo) {

        List<ProyectoEntity> lista = entityManager.createQuery("SELECT o FROM Proyecto o WHERE o.codigo = :codigo", ProyectoEntity.class).setParameter("codigo", codigo).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }
    
    // This is the central method called by the DataTable
    @Override
    @Transactional
    public List<ProyectoEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Proyecto o");

        // Can be optimized: We need this join only when tipoIdentificacion filter is set
        query.append(" LEFT OUTER JOIN o.oferta oferta");
        query.append(" LEFT OUTER JOIN o.empresa empresa");

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

                    case "oferta":
                        query.append(nextConnective).append(" o.oferta = :oferta");
                        queryParameters.put("oferta", filters.get(filterProperty));
                        break;

                    case "empresa":
                        query.append(nextConnective).append(" o.empresa = :empresa");
                        queryParameters.put("empresa", filters.get(filterProperty));
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

        TypedQuery<ProyectoEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    
    /********************/
    @Transactional
    public List<ProyectoEntity> findAllByEstado(EstadoProyectoEntity estado) {

        List<ProyectoEntity> lista = entityManager.createQuery("SELECT o FROM Proyecto o WHERE o.estadoProyecto = :estado", ProyectoEntity.class).setParameter("estado", estado).getResultList();
        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista;
    }
    
    

}
