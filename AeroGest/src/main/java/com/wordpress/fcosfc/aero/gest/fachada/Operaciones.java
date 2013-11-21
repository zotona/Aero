package com.wordpress.fcosfc.aero.gest.fachada;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.fachada.JsfUtil;
import com.wordpress.fcosfc.aero.gest.control.GestorOperaciones;
import com.wordpress.fcosfc.aero.gest.fachada.util.OperacionAerea;
import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ConversationScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Bean fachada de la vista para la gestión de listados de operaciones
 * 
 * @author fsaucedo
 */
@Named
@ConversationScoped
public class Operaciones extends BeanAbstracto implements Serializable {

    private static final Logger bitacora = Logger.getLogger(Operaciones.class.getName());
    
    @Inject
    private GestorOperaciones gestorOperaciones;
    
    private DataModel<OperacionAerea> operaciones;
    private Date fInicio, fFin;

    public Operaciones() {
        super();
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }

    protected GestorOperaciones getGestorOperaciones() {
        return gestorOperaciones;
    }

    public DataModel getOperaciones() {
        return operaciones;
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
            try {
                operaciones = new ListDataModel(OperacionAerea.getOperacionesAereas(getGestorOperaciones().getOperaciones(fInicio, fFin)));
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }
}
