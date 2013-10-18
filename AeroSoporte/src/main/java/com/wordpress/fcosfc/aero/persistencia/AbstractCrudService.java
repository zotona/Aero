package com.wordpress.fcosfc.aero.persistencia;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import javax.annotation.PostConstruct;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Bean DAO abstracto para el manejo de entidades
 * (http://www.javaworld.com/javaworld/jw-04-2009/jw-04-lean-soa-with-javaee6.html?page=6)
 *
 * @author Adam Bien, retocado y enriquecido por fsaucedo
 */
public abstract class AbstractCrudService extends BeanAbstracto {
    
    public AbstractCrudService() {
        super();
    }
    
    @PostConstruct
    @Override
    protected void inicializar() {
        if (getEntityManager() == null) {
            getBitacora().log(Level.SEVERE, "CrudService.inicializar: no se ha podido obtener una conexión a la base de datos");
        } else {
            getBitacora().log(Level.FINE, "Bean {0} {1} creado", new Object[]{this.getClass().getName(), this.hashCode()});
        }
    }

    protected abstract EntityManager getEntityManager();
    
    public <T> T create(T t) {
        getEntityManager().persist(t);
        getEntityManager().flush();
        getEntityManager().refresh(t);
        return t;
    }

    @SuppressWarnings("unchecked")
    public <T> T find(Class<T> type, Object id) {
        return (T) getEntityManager().find(type, id);
    }

    public void delete(Object t) {
        getEntityManager().remove(getEntityManager().merge(t));
    }

    public <T> T update(T t) {
        return (T) getEntityManager().merge(t);
    }

    public List findWithNamedQuery(String namedQueryName) {
        return getEntityManager().createNamedQuery(namedQueryName).getResultList();
    }

    public List findWithNamedQuery(String namedQueryName, Map<String, Object> parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    public List findWithNamedQuery(String queryName, int resultLimit) {
        return getEntityManager().createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    public List findByNativeQuery(String sql, Class type) {
        return getEntityManager().createNativeQuery(sql, type).getResultList();
    }

    public List findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = getEntityManager().createNamedQuery(namedQueryName);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    public <T> T findFirstRecordtWithNamedQuery(Class<T> type, String namedQueryName, Map<String, Object> parameters) {
        List<T> results;

        results = this.findWithNamedQuery(namedQueryName, parameters, 1);

        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }
}
