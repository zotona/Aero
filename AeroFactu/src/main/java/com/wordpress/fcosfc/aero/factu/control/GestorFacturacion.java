
package com.wordpress.fcosfc.aero.factu.control;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.factu.persistencia.CrudService;
import com.wordpress.fcosfc.aero.factu.persistencia.Factura;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Bean DAO para la gestión de listados de facturas
 * 
 * @author fsaucedo
 */
@Stateless
public class GestorFacturacion extends BeanAbstracto {
       
    private static final Logger bitacora = Logger.getLogger(GestorFacturacion.class.getName());
    
    @Inject
    private CrudService crudService;
    
    public GestorFacturacion() {
        super();
    } 

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }    
    
    protected CrudService getCrudService() {
        return crudService;
    }
    
    public List<Factura> getFacturas(Date fInicio, Date fFin) {
        Map<String, Object> parametros;

        parametros = new HashMap<String, Object>();
        parametros.put("fInicio", fInicio);
        parametros.put("fFin", fFin);

        return getCrudService().findWithNamedQuery("Factura.FindByFFactura", parametros);
    }
}
