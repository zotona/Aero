package com.wordpress.fcosfc.aero.factu.recursos;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;

/**
 * Bean que ofrece un recurso REST con representaciones de una factura
 * 
 * @author fsaucedo
 */
@Path("/factura")
@Stateless
public class Factura extends BeanAbstracto {
       
    private static final Logger bitacora = Logger.getLogger(Factura.class.getName());

    @PersistenceContext
    private EntityManager em;

    public Factura() {
        super();
    }
    
    @PostConstruct
    @Override
    protected void inicializar() {
        if (em == null) {
            getBitacora().log(Level.SEVERE, "AeroFactu --> Factura.inicializar: No se ha podido obtener una conexión a la base de datos");
        } else {
            getBitacora().log(Level.FINE, "Bean {0} {1} creado", new Object[]{this.getClass().getName(), this.hashCode()});
        }
    } 

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }    
    
    /**
     * Método que devuelve la representación de la factura en formato PDF
     * 
     * @param numFactura Número de la factura
     * @return Factura en formato PDF
     */
    @GET
    @Produces("application/pdf")
    public Response getFactura(@QueryParam("numero") long numFactura) {
        Response.ResponseBuilder responseBuilder;
        Map<String, Object> parametros;

        try {
            parametros = new HashMap<String, Object>();
            parametros.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
            parametros.put("numFactura", new Long(numFactura));

            responseBuilder = Response.ok(new ByteArrayInputStream(JasperRunManager.runReportToPdf(Factura.class.getResourceAsStream("factura.jasper"), parametros)));
        } catch (Exception ex) {
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
            getBitacora().log(Level.SEVERE, null, ex);           
        }

        return responseBuilder.build();
    }
}
