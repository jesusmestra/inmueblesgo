package com.j2km.inmueblesgo.service;


import com.j2km.inmueblesgo.domain.PermisoEntity;
import com.j2km.inmueblesgo.domain.TorreEntity;
import com.j2km.inmueblesgo.domain.UsuarioEntity;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.primefaces.model.SortOrder;

@Named
public class UsuarioService extends BaseService<UsuarioEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    public UsuarioService() {
        super(UsuarioEntity.class);
    }

    @Transactional
    public List<UsuarioEntity> findAllUsuarioEntities() {

        return entityManager.createQuery("SELECT o FROM Usuario o ", UsuarioEntity.class).getResultList();
    }

    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Usuario o", Long.class).getSingleResult();
    }

    @Override
    protected void handleDependenciesBeforeDelete(UsuarioEntity usuario) {

        /* This is called before a Usuario is deleted. Place here all the
           steps to cut dependencies to other entities */
        
        // 
    }  
    
    @Transactional
    public UsuarioEntity findByLogin(String login)  {
       
        List<UsuarioEntity> lista = entityManager.createQuery("FROM Usuario u WHERE u.login = :login", UsuarioEntity.class)
            .setParameter("login", login).getResultList();

        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista.get(0);
    }
    
    @Transactional
    public List<UsuarioEntity> findAllByLogin(String login)  {
       
        List<UsuarioEntity> lista = entityManager.createQuery("FROM Usuario u WHERE u.login like :login", UsuarioEntity.class)
            .setParameter("login", "%"+login+"%").getResultList();

        if (lista == null || lista.isEmpty()) {
            return null;
        }

        return lista;
    }
    
    @Override
    @Transactional
    public List<UsuarioEntity> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT o FROM Usuario o");

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

                    case "login":
                        query.append(nextConnective).append(" o.login LIKE :login");
                        queryParameters.put("login", "%" + filters.get(filterProperty) + "%");
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

        TypedQuery<UsuarioEntity> q = this.entityManager.createQuery(query.toString(), this.getType());

        for (String queryParameter : queryParameters.keySet()) {
            q.setParameter(queryParameter, queryParameters.get(queryParameter));
        }

        return q.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public Boolean esVendedor(UsuarioEntity usuario)  {
       
        List<PermisoEntity> lista = entityManager.
                createNamedQuery("UsuarioEntity.esVendedor", PermisoEntity.class).
                setParameter("usuario", usuario).
                getResultList();           

        return !(lista == null || lista.isEmpty());
    }
    
    @Transactional
    public Boolean esAdmin(UsuarioEntity usuario)  {
       
        List<PermisoEntity> lista = entityManager.
                createNamedQuery("UsuarioEntity.esAdmin", PermisoEntity.class).
                setParameter("usuario", usuario).
                getResultList();           

        return !(lista == null || lista.isEmpty());
    }

}
