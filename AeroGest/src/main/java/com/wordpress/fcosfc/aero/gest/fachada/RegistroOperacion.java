package com.wordpress.fcosfc.aero.gest.fachada;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.fachada.JsfUtil;
import com.wordpress.fcosfc.aero.gest.control.GestorRegistroOperacion;
import com.wordpress.fcosfc.aero.gest.fachada.util.OperacionAerea;
import com.wordpress.fcosfc.aero.gest.persistencia.Aerolinea;
import com.wordpress.fcosfc.aero.gest.persistencia.Operacion;
import com.wordpress.fcosfc.aero.gest.persistencia.Vuelo;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Bean fachada de la vista para la gestión de operaciones
 * 
 * @author fsaucedo
 */
@Named
@SessionScoped
public class RegistroOperacion extends BeanAbstracto implements Serializable {

    private static final Logger bitacora = Logger.getLogger(RegistroOperacion.class.getName());
    
    private static final String MARCADOR_NULO = "---"; 
    
    @Inject
    private GestorRegistroOperacion gestorRegistroOperacion;
    
    private Operacion operacionActual;
    private String codAerolinea, codVuelo;
    private boolean esSalida, esListaVuelosHabilitada;
    private DataModel<OperacionAerea> ultimasOperaciones;

    public RegistroOperacion() {
        super();
    }

    @PostConstruct
    @Override
    protected void inicializar() {
        try {
            inicializarDatos();
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

    protected GestorRegistroOperacion getGestorRegistroOperacion() {
        return gestorRegistroOperacion;
    }

    public Operacion getOperacionActual() {
        if (operacionActual == null) {
            operacionActual = new Operacion();
        }
        return operacionActual;
    }

    public List<SelectItem> getAerolineas() {
        List<Aerolinea> aerolineas;

        aerolineas = getGestorRegistroOperacion().getAerolineas();

        if (aerolineas.isEmpty()) {
            return null;
        } else {
            // La vista JSF no envía valores null en llamadas Ajax
            List<SelectItem> resultado;
            String seleccioneAerolinea;

            seleccioneAerolinea = ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("operacionRegistrarSeleccionarAerolinea");

            resultado = new ArrayList(aerolineas.size() + 1);
            resultado.add(new SelectItem(MARCADOR_NULO, seleccioneAerolinea, seleccioneAerolinea));
            for (Aerolinea aerolinea : aerolineas) {
                resultado.add(new SelectItem(aerolinea.getCodAerolinea(), aerolinea.getNombre(), aerolinea.getNombre()));
            }

            return resultado;
        }
    }

    public String getCodAerolinea() {
        return codAerolinea;
    }

    public void setCodAerolinea(String codAerolinea) {
        this.codAerolinea = codAerolinea;
    }

    public List<SelectItem> getVuelos() {
        if (codAerolinea.equals(MARCADOR_NULO)) {
            esListaVuelosHabilitada = false;

            return null;
        } else {
            List<Vuelo> vuelos;

            vuelos = getGestorRegistroOperacion().getVuelosAerolinea(codAerolinea, esSalida);

            if (vuelos.isEmpty()) {
                esListaVuelosHabilitada = false;

                return null;
            } else {
                List<SelectItem> resultado;
                String seleccioneVuelo, etiquetaVuelo;
                DateFormat dateFormat;

                seleccioneVuelo = ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("operacionRegistrarSeleccionarVuelo");
                dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

                resultado = new ArrayList(vuelos.size() + 1);
                resultado.add(new SelectItem(null, seleccioneVuelo, seleccioneVuelo));
                for (Vuelo vuelo : vuelos) {
                    etiquetaVuelo = vuelo.getLocalidad().getNombre() + " " + dateFormat.format(vuelo.getHora());
                    resultado.add(new SelectItem(vuelo.getCodVuelo(), etiquetaVuelo, etiquetaVuelo));
                }

                esListaVuelosHabilitada = true;

                return resultado;
            }
        }
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
        if (codVuelo != null && operacionActual.getFOperacion() == null) {
            Calendar calendar;
            DateFormat dateFormat;
            String fechaHora;
            Vuelo vuelo;

            calendar = Calendar.getInstance();
            operacionActual.setFOperacion(calendar.getTime());
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
            fechaHora = dateFormat.format(operacionActual.getFOperacion());

            vuelo = getGestorRegistroOperacion().getVuelo(codVuelo);
            dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            fechaHora = fechaHora + " " + dateFormat.format(vuelo.getHora());

            dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            try {
                operacionActual.setFOperacion(dateFormat.parse(fechaHora));
            } catch (ParseException ex) {
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isEsSalida() {
        return esSalida;
    }

    public void setEsSalida(boolean esSalida) {
        this.esSalida = esSalida;
    }

    public boolean isEsListaVuelosHabilitada() {
        return esListaVuelosHabilitada;
    }

    public DataModel getUltimasOperaciones() {
        return ultimasOperaciones;
    }

    public String prepararRegistro() {
        try {
            inicializarDatos();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
        }

        return "Registrar";
    }

    public String registrar() {
        try {
            operacionActual.setVuelo(getGestorRegistroOperacion().getVuelo(codVuelo));
            getGestorRegistroOperacion().registrar(operacionActual);
            inicializarDatos();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroGuardado"));
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorRegistroOperacionDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void inicializarDatos() {
        operacionActual = new Operacion();
        operacionActual.setNumPasajeros(0);
        codAerolinea = MARCADOR_NULO;
        codVuelo = null;
        esSalida = false;
        esListaVuelosHabilitada = false;
        ultimasOperaciones = new ListDataModel<OperacionAerea>(OperacionAerea.getOperacionesAereas(getGestorRegistroOperacion().getUltimasOperaciones()));
    }
}
