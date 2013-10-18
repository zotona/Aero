
package com.wordpress.fcosfc.aero.factu.control;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.factu.jms.ConectorBusMensajes;
import com.wordpress.fcosfc.aero.factu.persistencia.Cliente;
import com.wordpress.fcosfc.aero.factu.persistencia.CrudService;
import com.wordpress.fcosfc.aero.factu.persistencia.Factura;
import com.wordpress.fcosfc.aero.factu.persistencia.LineaFactura;
import com.wordpress.fcosfc.aero.xml.Operacion;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * Bean con métodos de facturación
 * 
 * @author fsaucedo
 */
@Stateless
public class Facturador extends BeanAbstracto {
       
    private static final Logger bitacora = Logger.getLogger(Facturador.class.getName());
    
    private static final String UTF8 = "UTF8";
    private static final String CONCEPTO_PAX_EMBARQUE = "Tasa por pasajeros en embarque";
    private static final String CONCEPTO_PAX_DESEMBARQUE = "Tasa por pasajeros en desembarque";
    private static final String CONCEPTO_USO_AEROPUERTO = "Uso aeropuerto";
    private static final Double IMP_UD_PAX_EMBARQUE = new Double(1);
    private static final Double IMP_UD_PAX_DESEMBARQUE = new Double(0.5);
    private static final Double IMP_USO_AEROPUERTO = new Double(20);
        
    @Inject
    private CrudService crudService;
    
    @Inject
    private ConectorBusMensajes conectorBusMensajes;
    
    public Facturador() {
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
     * Método que factura una operación aérea, registra la factura y la comunica
     * para que otras aplicaciones puedan tratarla, por ejemplo un sistema de
     * administración electrónica
     * 
     * Sobra decirlo, pero, por si acaso, aclaro que el ejemplo es muy, muy 
     * simplificado, en absoluto real,con conceptos e importes puestos a fuego
     * en código
     * 
     * @param mensajeOperacion Mensaje con la operación aérea
     */
    public void facturar(TextMessage mensajeOperacion) {
        JAXBContext jaxbContext;
        Unmarshaller unmarshaller;
        Operacion operacion;
        Cliente cliente;
        Factura factura;
        LineaFactura lineaFactura;
        String referencia;
        DateFormat dateFormat;
        
        try {
            jaxbContext = JAXBContext.newInstance(com.wordpress.fcosfc.aero.xml.Operacion.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            operacion = (Operacion) unmarshaller.unmarshal(new ByteArrayInputStream(mensajeOperacion.getText().getBytes(UTF8)));
            
            dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            if (operacion.getVuelo().isEsSalida()) {
                referencia = "Salida vuelo con destino ";
            } else {
                referencia = "Llegada vuelo con origen ";
            }
            referencia += operacion.getVuelo().getLocalidad().getNombre() + ", ";
            referencia += dateFormat.format(operacion.getVuelo().getHora());
            
            cliente = getCrudService().find(Cliente.class, operacion.getVuelo().getAerolinea().getCodAerolinea());
            if (cliente == null) {
                cliente = new Cliente(operacion.getVuelo().getAerolinea().getCodAerolinea(), operacion.getVuelo().getAerolinea().getNombre());
                getCrudService().create(cliente);
            }
            
            factura = new Factura();
            factura.setCliente(cliente);
            factura.setfFactura(operacion.getFOperacion());
            factura.setReferencia(referencia);
            factura = getCrudService().create(factura);
            
            lineaFactura = new LineaFactura();
            lineaFactura.setConcepto(CONCEPTO_USO_AEROPUERTO);
            lineaFactura.setUnidades(1);
            lineaFactura.setImporteUnidad(IMP_USO_AEROPUERTO);
            lineaFactura.setFactura(factura);
            factura.setImporteTotal(lineaFactura.getImporteTotal());
            getCrudService().create(lineaFactura);
            
            lineaFactura = new LineaFactura();
            if (operacion.getVuelo().isEsSalida()) {
                lineaFactura.setConcepto(CONCEPTO_PAX_EMBARQUE);
                lineaFactura.setImporteUnidad(IMP_UD_PAX_EMBARQUE);
            } else {                
                lineaFactura.setConcepto(CONCEPTO_PAX_DESEMBARQUE);
                lineaFactura.setImporteUnidad(IMP_UD_PAX_DESEMBARQUE);
            }
            lineaFactura.setUnidades(operacion.getNumPasajeros());
            lineaFactura.setFactura(factura);
            factura.setImporteTotal(factura.getImporteTotal() + lineaFactura.getImporteTotal());
            getCrudService().create(lineaFactura);
            
            getCrudService().update(factura);            
            getBitacora().log(Level.INFO, "AeroFactu --> Facturador.facturar: factura número {0} generada para la operación con identificador {1}, con un importe de {2}", new Object[] {factura.getNumFactura(), operacion.getIdOperacion(), factura.getImporteTotal()});
            
            conectorBusMensajes.enviarMensaje(factura);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
