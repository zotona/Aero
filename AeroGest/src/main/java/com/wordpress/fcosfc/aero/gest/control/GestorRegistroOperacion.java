package com.wordpress.fcosfc.aero.gest.control;

import com.wordpress.fcosfc.aero.control.GestorAbstracto;
import com.wordpress.fcosfc.aero.gest.jms.ConectorBusMensajes;
import com.wordpress.fcosfc.aero.gest.persistencia.Aerolinea;
import com.wordpress.fcosfc.aero.gest.persistencia.CrudService;
import com.wordpress.fcosfc.aero.gest.persistencia.Operacion;
import com.wordpress.fcosfc.aero.gest.persistencia.Vuelo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Bean DAO para la gestión de operaciones
 * 
 * @author fsaucedo
 */
@Stateless
public class GestorRegistroOperacion extends GestorAbstracto<Operacion> {

    private static final Logger bitacora = Logger.getLogger(GestorRegistroOperacion.class.getName());
    
    private static final int NUM_OPERACIONES = 5;
    
    @Inject
    private CrudService crudService;

    @Inject 
    private ConectorBusMensajes conectorBusMensajes;

    public GestorRegistroOperacion() {
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
    
    protected ConectorBusMensajes getConectorBusMensajes() {
        return conectorBusMensajes;
    }
    
    public List<Aerolinea> getAerolineas() {
        return getCrudService().findWithNamedQuery("Aerolinea.FindAll");
    }
    
    public Vuelo getVuelo(String codVuelo) {
        return getCrudService().find(Vuelo.class, codVuelo);
    }
    
    public List<Vuelo> getVuelosAerolinea(String codAerolinea, boolean esSalida) {
        Map<String, Object> parametros;

        parametros = new HashMap<String, Object>();
        parametros.put("codAerolinea", codAerolinea);
        parametros.put("esSalida", esSalida);

        return getCrudService().findWithNamedQuery("Vuelo.FindByAerolineaYEsSalida", parametros);
    }
    
    public List<Operacion> getUltimasOperaciones() {
        return getCrudService().findWithNamedQuery("Operacion.FindAll", NUM_OPERACIONES);
    }
    
    /*
     * Método que registra una operación aérea.
     * 
     * Primero le da persistencia y luego comunica el registro al resto de 
     * aplicaciones asociadas. Las dos operaciones se realizan en el contexto
     * de una transacción distribuida XA, ya que la tecnología EJB inicia por 
     * defecto una transacción cuando se entra en el método, si se produce una
     * excepción se hace rollback
     */
    public void registrar(Operacion operacion) {
        crear(operacion);
        getConectorBusMensajes().enviarMensaje(operacion);
    }
}
