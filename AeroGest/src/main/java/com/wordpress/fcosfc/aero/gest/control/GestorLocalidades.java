package com.wordpress.fcosfc.aero.gest.control;

import com.wordpress.fcosfc.aero.control.GestorAbstracto;
import com.wordpress.fcosfc.aero.gest.persistencia.CrudService;
import com.wordpress.fcosfc.aero.gest.persistencia.Localidad;
import com.wordpress.fcosfc.aero.gest.persistencia.Pais;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Bean DAO para la gestión de localidades
 * 
 * @author fsaucedo
 */
@Stateless
public class GestorLocalidades extends GestorAbstracto<Localidad> {

    private static final Logger bitacora = Logger.getLogger(GestorLocalidades.class.getName());
    
    @Inject
    private CrudService crudService;

    public GestorLocalidades() {
        super(Localidad.class);
    }    

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }    

    @Override
    protected CrudService getCrudService() {
        return crudService;
    }

    public List<Localidad> filtrarPorPaisYNombre(String codISOPais, String filtro) {
        Map<String, Object> parametros;

        parametros = new HashMap<String, Object>();
        parametros.put("codISOPais", codISOPais);
        parametros.put("filtro", filtro);

        return getCrudService().findWithNamedQuery("Localidad.FindByFiltroPorCodISOPaisYNombre", parametros);
    }

    public Pais getPais(String codISOPais) {
        return getCrudService().find(Pais.class, codISOPais);
    }

    public List<Pais> getPaises() {
        return getCrudService().findWithNamedQuery("Pais.FindAll");
    }
}
