package com.wordpress.fcosfc.aero.estad.control;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.estad.persistencia.Aerolinea;
import com.wordpress.fcosfc.aero.estad.persistencia.Anyo;
import com.wordpress.fcosfc.aero.estad.persistencia.CrudService;
import com.wordpress.fcosfc.aero.estad.persistencia.Operacion;
import com.wordpress.fcosfc.aero.estad.persistencia.Pais;
import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * Bean con métodos de registro de estadísticas
 * 
 * @author fsaucedo
 */
@Stateless
public class RegistradorEstadisticas extends BeanAbstracto {
       
    private static final Logger bitacora = Logger.getLogger(RegistradorEstadisticas.class.getName());
    
    private static final String UTF8 = "UTF8";
    
    @Inject
    private CrudService crudService;
    
    public RegistradorEstadisticas() {
        super();
    }  

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }   
    
    protected CrudService getCrudService() {
        return crudService;
    }
    
    /**
     * Método que crea un registro estadístico para la operación aérea
     * 
     * @param mensajeOperacion Mensaje con la operación aérea
     */
    public void registrar(TextMessage mensajeOperacion) {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        Anyo anyo;
        Aerolinea aerolinea;
        Pais pais;
        Operacion operacionEstadistica;
        com.wordpress.fcosfc.aero.xml.Operacion operacionAerea;
        Calendar calendario;
        
        try {
            jaxbContext = JAXBContext.newInstance(com.wordpress.fcosfc.aero.xml.Operacion.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            operacionAerea = (com.wordpress.fcosfc.aero.xml.Operacion) unmarshaller.unmarshal(new ByteArrayInputStream(mensajeOperacion.getText().getBytes(UTF8)));
            
            calendario = Calendar.getInstance();
            calendario.setTime(operacionAerea.getFOperacion());
            
            operacionEstadistica = new Operacion();
            
            anyo = getCrudService().find(Anyo.class, calendario.get(Calendar.YEAR));
            if (anyo == null) {
                anyo = new Anyo(calendario.get(Calendar.YEAR));
                getCrudService().create(anyo);
            }
            operacionEstadistica.setAnyo(anyo);
            operacionEstadistica.setMes(calendario.get(Calendar.MONTH) + 1);    // Por motivo desconocido, el índice empieza por 0
            
            aerolinea = getCrudService().find(Aerolinea.class, operacionAerea.getVuelo().getAerolinea().getCodAerolinea());
            if (aerolinea == null) {
                aerolinea = new Aerolinea();
                aerolinea.setCodAerolinea(operacionAerea.getVuelo().getAerolinea().getCodAerolinea());
                aerolinea.setNombre(operacionAerea.getVuelo().getAerolinea().getNombre());
                getCrudService().create(aerolinea);
            }
            operacionEstadistica.setAerolinea(aerolinea);
            
            pais = getCrudService().find(Pais.class, operacionAerea.getVuelo().getLocalidad().getPais().getCodISOPais());
            if (pais == null) {
                pais = new Pais();
                pais.setCodISOPais(operacionAerea.getVuelo().getLocalidad().getPais().getCodISOPais());
                pais.setNombre(operacionAerea.getVuelo().getLocalidad().getPais().getNombre());
                getCrudService().create(pais);
            }
            operacionEstadistica.setPais(pais);
            
            operacionEstadistica.setEsSalida(operacionAerea.getVuelo().isEsSalida());
            operacionEstadistica.setNumPasajeros(operacionAerea.getNumPasajeros());
            getCrudService().create(operacionEstadistica);
            
            getBitacora().log(Level.INFO, "AeroEstad --> RegistradorEstadisticas.registrar: registro estadístico con identificador {0} generado para la operación con identificador {1}", new Object[] { operacionEstadistica.getIdOperacion(), operacionAerea.getIdOperacion() });
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }    
    }
}
