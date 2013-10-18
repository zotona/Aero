package com.wordpress.fcosfc.aero.gest.control;

import com.wordpress.fcosfc.aero.control.GestorAbstracto;
import com.wordpress.fcosfc.aero.gest.persistencia.CrudService;
import com.wordpress.fcosfc.aero.gest.persistencia.Aerolinea;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Bean DAO para la gestión de aerolíneas
 * 
 * @author fsaucedo
 */
@Stateless
public class GestorAerolineas extends GestorAbstracto<Aerolinea> {
       
    private static final Logger bitacora = Logger.getLogger(GestorAerolineas.class.getName());
    
    @Inject
    private CrudService crudService;
    
    public GestorAerolineas() {
        super(Aerolinea.class);
    }    

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }    

    @Override
    protected CrudService getCrudService() {
        return crudService;
    }
    
    public List<Aerolinea> filtrarPorNombre(String filtro) {
        Map<String, Object> parametros;
        
        parametros = new HashMap<String, Object>();
        parametros.put("filtro", filtro);
                
        return getCrudService().findWithNamedQuery("Aerolinea.FindByFiltroPorNombre", parametros);
    }
}
