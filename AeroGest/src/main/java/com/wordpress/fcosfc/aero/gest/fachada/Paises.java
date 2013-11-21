package com.wordpress.fcosfc.aero.gest.fachada;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.fachada.JsfUtil;
import com.wordpress.fcosfc.aero.gest.control.GestorPaises;
import com.wordpress.fcosfc.aero.gest.persistencia.Pais;
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
 * Bean fachada de la vista para la gestión de países
 * 
 * @author fsaucedo
 */
@Named
@SessionScoped
public class Paises extends BeanAbstracto implements Serializable {

    private static final Logger bitacora = Logger.getLogger(Paises.class.getName());
    
    @Inject
    private GestorPaises gestorPaises;
    
    private Pais paisActual;
    private DataModel<Pais> paises;
    private String filtro;

    public Paises() {
        super();
    }

    @PostConstruct
    @Override
    protected void inicializar() {
        filtro = "%";
        try {
            refrescarPaises();
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

    protected GestorPaises getGestorPaises() {
        return gestorPaises;
    }

    public Pais getPaisActual() {
        if (paisActual == null) {
            paisActual = new Pais();
        }
        return paisActual;
    }

    public DataModel<Pais> getPaises() {
        return paises;
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
                refrescarPaises();
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String prepararCreacion() {
        paisActual = new Pais();

        return "Crear";
    }

    public String crear() {
        try {
            getGestorPaises().crear(this.paisActual);
            refrescarPaises();
            paisActual = new Pais();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroGuardado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);

            return null;
        }
    }

    public String prepararEditar() {
        paisActual = getPaises().getRowData();

        return "Editar";
    }

    public String actualizar() {
        try {
            getGestorPaises().actualizar(paisActual);
            refrescarPaises();

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
            paisActual = getPaises().getRowData();
            getGestorPaises().borrar(paisActual);
            refrescarPaises();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroBorrado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
            return null;
        }
    }

    protected void refrescarPaises() {
        paises = new ListDataModel<Pais>(getGestorPaises().filtrarPorNombre(filtro.toUpperCase()));
    }
}
