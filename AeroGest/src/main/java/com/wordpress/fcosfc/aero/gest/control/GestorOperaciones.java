package com.wordpress.fcosfc.aero.gest.control;

import com.wordpress.fcosfc.aero.control.GestorAbstracto;
import com.wordpress.fcosfc.aero.gest.persistencia.CrudService;
import com.wordpress.fcosfc.aero.gest.persistencia.Operacion;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Bean DAO para la gestión de listados de operaciones
 * 
 * @author fsaucedo
 */
@Stateless
public class GestorOperaciones extends GestorAbstracto<Operacion> {

    private static final Logger bitacora = Logger.getLogger(GestorOperaciones.class.getName());
    
    @Inject
    private CrudService crudService;
    
    public GestorOperaciones() {
        super(Operacion.class);
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }

    @Override
    protected CrudService getCrudService() {
        return crudService;
    }

    public List<Operacion> getOperaciones(Date fInicio, Date fFin) {
        Map<String, Object> parametros;

        parametros = new HashMap<String, Object>();
        parametros.put("fInicio", fInicio);
        parametros.put("fFin", fFin);

        return getCrudService().findWithNamedQuery("Operacion.FindByFOperacion", parametros);
    }
}
