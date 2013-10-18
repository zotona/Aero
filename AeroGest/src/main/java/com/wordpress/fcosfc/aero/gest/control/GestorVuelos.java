package com.wordpress.fcosfc.aero.gest.control;

import com.wordpress.fcosfc.aero.control.GestorAbstracto;
import com.wordpress.fcosfc.aero.gest.persistencia.Aerolinea;
import com.wordpress.fcosfc.aero.gest.persistencia.CrudService;
import com.wordpress.fcosfc.aero.gest.persistencia.Vuelo;
import com.wordpress.fcosfc.aero.gest.persistencia.Localidad;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Bean DAO para la gestión de vuelos
 * 
 * @author fsaucedo
 */
@Stateless
public class GestorVuelos extends GestorAbstracto<Vuelo> {

    private static final Logger bitacora = Logger.getLogger(GestorVuelos.class.getName());
    
    @Inject
    private CrudService crudService;

    public GestorVuelos() {
        super(Vuelo.class);
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }
    
    @Override
    protected CrudService getCrudService() {
        return crudService;
    }

    public List<Vuelo> filtrarPorAerolinea(String codAerolinea) {
        Map<String, Object> parametros;

        parametros = new HashMap<String, Object>();
        parametros.put("codAerolinea", codAerolinea);

        return getCrudService().findWithNamedQuery("Vuelo.FindByAerolinea", parametros);
    }

    public Aerolinea getAerolinea(String codAerolinea) {
        return getCrudService().find(Aerolinea.class, codAerolinea);
    }

    public List<Aerolinea> getAerolineas() {
        return getCrudService().findWithNamedQuery("Aerolinea.FindAll");
    }

    public Localidad getLocalidad(String codISOLocalidad) {
        return getCrudService().find(Localidad.class, codISOLocalidad);
    }

    public List<Localidad> getLocalidades() {
        return getCrudService().findWithNamedQuery("Localidad.FindAll");
    }
}
