package com.wordpress.fcosfc.aero.factu.fachada;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.fachada.JsfUtil;
import com.wordpress.fcosfc.aero.factu.control.GestorFacturacion;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang.time.DateUtils;

/**
 * Bean fachada de la vista para listados de facturas
 * 
 * @author fsaucedo
 */
@Named
@RequestScoped
public class Facturacion extends BeanAbstracto implements Serializable {
       
    private static final Logger bitacora = Logger.getLogger(Facturacion.class.getName());

    @Inject
    private GestorFacturacion gestorFacturacion;
    
    private DataModel facturas;
    private Date fInicio, fFin;

    public Facturacion() {
        super();
    }

    @PostConstruct
    @Override
    protected void inicializar() {
        fInicio = new Date(Calendar.getInstance().getTimeInMillis());
        fInicio = DateUtils.truncate(fInicio, Calendar.DAY_OF_MONTH);
        fFin = fInicio;
        refrescarListaFacturas();
        
        getBitacora().log(Level.FINE, "Bean {0} {1} creado", new Object[]{this.getClass().getName(), this.hashCode()});
    }  

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }   

    protected GestorFacturacion getGestorFacturacion() {
        return gestorFacturacion;
    }

    public DataModel getFacturas() {
        return facturas;
    }

    public Date getFInicio() {
        return fInicio;
    }

    public void setFInicio(Date fInicio) {
        this.fInicio = fInicio;
    }

    public Date getFFin() {
        return fFin;
    }

    public void setFFin(Date fFin) {
        this.fFin = fFin;
    }

    public String filtrar() {
        if (fInicio.after(fFin)) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("operacionesFInicioNoValida"));
        } else {
            refrescarListaFacturas();
        }

        return null;
    }

    private void refrescarListaFacturas() {
        try {
            fFin = DateUtils.addHours(fFin, 23);
            fFin = DateUtils.addMinutes(fFin, 59);
            facturas = new ListDataModel(getGestorFacturacion().getFacturas(fInicio, fFin));
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/factu/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
        }
    }
}
