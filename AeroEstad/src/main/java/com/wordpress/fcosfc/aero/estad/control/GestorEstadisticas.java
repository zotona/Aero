package com.wordpress.fcosfc.aero.estad.control;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.estad.fachada.util.LineaListadoEstadistica;
import com.wordpress.fcosfc.aero.estad.persistencia.Anyo;
import com.wordpress.fcosfc.aero.estad.persistencia.CrudService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Bean DAO para la gestión de listados estadísticos
 * 
 * @author fsaucedo
 */
@Stateless
public class GestorEstadisticas extends BeanAbstracto {
       
    private static final Logger bitacora = Logger.getLogger(GestorEstadisticas.class.getName());
    
    @Inject
    private CrudService crudService;
    
    public GestorEstadisticas() {
        super();
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }    
    
    protected CrudService getCrudService() {
        return crudService;
    }
    
    public List<Anyo> getAnyos() {
        return getCrudService().findWithNamedQuery("Anyo.FindAll");
    }
    
    public List<LineaListadoEstadistica> getListado(Integer anyo) {
        List<LineaListadoEstadistica> resultado = null;        
        List<Object[]> listado;
        Map<String, Object> parametros;

        parametros = new HashMap<String, Object>();
        parametros.put("anyo", new Anyo(anyo));
        
        listado = getCrudService().findWithNamedQuery("Operacion.FindByAnyo", parametros);
        if (listado != null) {
            LineaListadoEstadistica lineaResultado;
            
            resultado = new ArrayList<LineaListadoEstadistica>(listado.size());
            for (Object[] lineaOrigen : listado) {
                lineaResultado = new LineaListadoEstadistica();
                lineaResultado.setMes(getEtiquetaMes(((Integer) lineaOrigen[0]).intValue()));
                lineaResultado.setAerolinea((String) lineaOrigen[1]);
                lineaResultado.setPais((String) lineaOrigen[2]);
                lineaResultado.setTipoOperacion(((Boolean) lineaOrigen[3]).booleanValue() == true ? "Salida" : "Entrada");
                lineaResultado.setNumPasajeros((Long) lineaOrigen[4]);
                resultado.add(lineaResultado);
            }
        }
                
        return resultado;
    }
    
    private String getEtiquetaMes(int mes) {
        String[] listaMeses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", 
                                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };
        
        return listaMeses[mes - 1];
    }
}
