package com.wordpress.fcosfc.aero.gest.persistencia;

import com.wordpress.fcosfc.aero.persistencia.AbstractCrudService;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Bean DAO para el manejo de entidades
 * (http://www.javaworld.com/javaworld/jw-04-2009/jw-04-lean-soa-with-javaee6.html?page=6)
 *
 * @author Adam Bien, retocado y enriquecido por fsaucedo
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CrudService extends AbstractCrudService {

    private static final Logger bitacora = Logger.getLogger(CrudService.class.getName());
    
    @PersistenceContext
    private EntityManager em;

    public CrudService() {
        super();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }
}
