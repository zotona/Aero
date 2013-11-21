package com.wordpress.fcosfc.aero.gest.fachada;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.fachada.JsfUtil;
import com.wordpress.fcosfc.aero.gest.control.GestorLocalidades;
import com.wordpress.fcosfc.aero.gest.persistencia.Localidad;
import com.wordpress.fcosfc.aero.gest.persistencia.Pais;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Bean fachada de la vista para la gestión de localidades
 * 
 * @author fsaucedo
 */
@Named
@SessionScoped
public class Localidades extends BeanAbstracto implements Serializable {

    private static final Logger bitacora = Logger.getLogger(Localidades.class.getName());
    
    @Inject
    private GestorLocalidades gestorLocalidades;
    
    private Localidad localidadActual;
    private DataModel<Localidad> localidades;
    private String codISOPais, filtro;

    public Localidades() {
        super();
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }
    
    protected GestorLocalidades getGestorLocalidades() {
        return gestorLocalidades;
    }

    public Localidad getLocalidadActual() {
        if (localidadActual == null) {
            localidadActual = new Localidad();
        }
        return localidadActual;
    }

    public DataModel<Localidad> getLocalidades() {
        return localidades;
    }

    public List<Pais> getPaises() {
        return getGestorLocalidades().getPaises();
    }

    public String getCodISOPais() {
        return codISOPais;
    }

    public void setCodISOPais(String codISOPais) {
        this.codISOPais = codISOPais;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String filtrar() {
        if (codISOPais != null && filtro != null) {
            try {
                refrescarLocalidades();
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void prepararLista(String codISOPais) {
        this.codISOPais = codISOPais;
        filtro = "%";
        try {
            refrescarLocalidades();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
        }
    }

    public String prepararCreacion() {
        localidadActual = new Localidad();

        return "Crear";
    }

    public String crear() {
        try {
            localidadActual.setPais(getGestorLocalidades().getPais(codISOPais));
            getGestorLocalidades().crear(localidadActual);
            refrescarLocalidades();
            localidadActual = new Localidad();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroGuardado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);

            return null;
        }
    }

    public String prepararEditar() {
        localidadActual = getLocalidades().getRowData();

        return "Editar";
    }

    public String actualizar() {
        try {
            localidadActual.setPais(getGestorLocalidades().getPais(codISOPais));
            getGestorLocalidades().actualizar(localidadActual);
            refrescarLocalidades();

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
            localidadActual = getLocalidades().getRowData();
            getGestorLocalidades().borrar(localidadActual);
            refrescarLocalidades();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroBorrado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void refrescarLocalidades() {
        localidades = new ListDataModel<Localidad>(getGestorLocalidades().filtrarPorPaisYNombre(codISOPais, filtro.toUpperCase()));
    }
}
