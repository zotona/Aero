package com.wordpress.fcosfc.aero.gest.control;

import com.wordpress.fcosfc.aero.control.GestorAbstracto;
import com.wordpress.fcosfc.aero.gest.persistencia.CrudService;
import com.wordpress.fcosfc.aero.gest.persistencia.Pais;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Bean DAO para la gestión de países
 * 
 * @author fsaucedo
 */
@Stateless
public class GestorPaises extends GestorAbstracto<Pais> {
    
    private static final Logger bitacora = Logger.getLogger(GestorPaises.class.getName());
    
    @Inject
    private CrudService crudService;
    
    public GestorPaises() {
        super(Pais.class);
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }
    
    @Override
    protected CrudService getCrudService() {
        return crudService;
    }
    
    public List<Pais> filtrarPorNombre(String filtro) {
        Map<String, Object> parametros;
        
        parametros = new HashMap<String, Object>();
        parametros.put("filtro", filtro);
                
        return getCrudService().findWithNamedQuery("Pais.FindByFiltroPorNombre", parametros);
    }
}
