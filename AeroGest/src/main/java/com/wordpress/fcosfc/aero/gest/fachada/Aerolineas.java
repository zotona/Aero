package com.wordpress.fcosfc.aero.gest.fachada;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.fachada.JsfUtil;
import com.wordpress.fcosfc.aero.gest.control.GestorAerolineas;
import com.wordpress.fcosfc.aero.gest.persistencia.Aerolinea;
import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Bean fachada de la vista para la gestión de aerolíneas
 * 
 * @author fsaucedo
 */
@Named
@SessionScoped
public class Aerolineas extends BeanAbstracto implements Serializable {

    private static final Logger bitacora = Logger.getLogger(Aerolineas.class.getName());
    
    @Inject
    private GestorAerolineas gestorAerolineas;
    
    private Aerolinea aerolineaActual;
    private DataModel aerolineas;
    private String filtro;

    public Aerolineas() {
        super();
    }

    @PostConstruct
    @Override
    protected void inicializar() {
        filtro = "%";
        try {
            refrescarAerolineas();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
        }

        getBitacora().log(Level.FINE, "Bean {0} {1} creado", new Object[]{this.getClass().getName(), this.hashCode()});
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }

    protected GestorAerolineas getGestorAerolineas() {
        return gestorAerolineas;
    }

    public Aerolinea getAerolineaActual() {
        if (aerolineaActual == null) {
            aerolineaActual = new Aerolinea();
        }
        return aerolineaActual;
    }

    public DataModel getAerolineas() {
        return aerolineas;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String filtrar() {
        if (filtro != null) {
            try {
                refrescarAerolineas();
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String prepararCreacion() {
        aerolineaActual = new Aerolinea();

        return "Crear";
    }

    public String crear() {
        try {
            getGestorAerolineas().crear(this.aerolineaActual);
            refrescarAerolineas();
            aerolineaActual = new Aerolinea();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroGuardado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);

            return null;
        }
    }

    public String prepararEditar() {
        aerolineaActual = (Aerolinea) getAerolineas().getRowData();

        return "Editar";
    }

    public String actualizar() {
        try {
            getGestorAerolineas().actualizar(aerolineaActual);
            refrescarAerolineas();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroActualizado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String borrar() {
        try {
            aerolineaActual = (Aerolinea) getAerolineas().getRowData();
            getGestorAerolineas().borrar(aerolineaActual);
            refrescarAerolineas();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroBorrado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void refrescarAerolineas() {
        aerolineas = new ListDataModel(getGestorAerolineas().filtrarPorNombre(filtro.toUpperCase()));
    }
}
