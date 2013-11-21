package com.wordpress.fcosfc.aero.gest.fachada;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.fachada.JsfUtil;
import com.wordpress.fcosfc.aero.gest.control.GestorVuelos;
import com.wordpress.fcosfc.aero.gest.persistencia.Aerolinea;
import com.wordpress.fcosfc.aero.gest.persistencia.Localidad;
import com.wordpress.fcosfc.aero.gest.persistencia.Vuelo;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Bean fachada de la vista para la gestión de vuelos
 * 
 * @author fsaucedo
 */
@Named
@SessionScoped
public class Vuelos extends BeanAbstracto implements Serializable {

    private static final Logger bitacora = Logger.getLogger(Vuelos.class.getName());
    
    @Inject
    private GestorVuelos gestorVuelos;
    
    private Vuelo vueloActual;
    private DataModel<Vuelo> vuelos;
    private String codAerolinea, codISOLocalidad, hora, minuto;

    public Vuelos() {
        super();
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }

    protected GestorVuelos getGestorVuelos() {
        return gestorVuelos;
    }

    public Vuelo getVueloActual() {
        if (vueloActual == null) {
            vueloActual = new Vuelo();
        }
        return vueloActual;
    }

    public String filtrar() {
        if (codAerolinea != null) {
            try {
                refrescarVuelos();
            } catch (Exception ex) {
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }

        return null;
    }

    public void prepararLista(String codAerolinea) {
        this.codAerolinea = codAerolinea;
        try {
            refrescarVuelos();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
        }
    }

    public DataModel<Vuelo> getVuelos() {
        return vuelos;
    }

    public List<Aerolinea> getAerolineas() {
        return getGestorVuelos().getAerolineas();
    }

    public String getCodAerolinea() {
        return codAerolinea;
    }

    public void setCodAerolinea(String codAerolinea) {
        this.codAerolinea = codAerolinea;
    }

    public List<Localidad> getLocalidades() {
        return getGestorVuelos().getLocalidades();
    }

    public String getCodISOLocalidad() {
        return codISOLocalidad;
    }

    public void setCodISOLocalidad(String codISOLocalidad) {
        this.codISOLocalidad = codISOLocalidad;
    }

    public List<String> getHoras() {
        List<String> listaHoras;

        listaHoras = new ArrayList<String>(24);
        listaHoras.add("0");
        listaHoras.add("1");
        listaHoras.add("2");
        listaHoras.add("3");
        listaHoras.add("4");
        listaHoras.add("5");
        listaHoras.add("6");
        listaHoras.add("7");
        listaHoras.add("8");
        listaHoras.add("9");
        listaHoras.add("10");
        listaHoras.add("11");
        listaHoras.add("12");
        listaHoras.add("13");
        listaHoras.add("14");
        listaHoras.add("15");
        listaHoras.add("16");
        listaHoras.add("17");
        listaHoras.add("18");
        listaHoras.add("19");
        listaHoras.add("20");
        listaHoras.add("21");
        listaHoras.add("22");
        listaHoras.add("23");

        return listaHoras;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public List<String> getMinutos() {
        List<String> listaMinutos;

        listaMinutos = new ArrayList<String>(60);
        listaMinutos.add("00");
        listaMinutos.add("01");
        listaMinutos.add("02");
        listaMinutos.add("03");
        listaMinutos.add("04");
        listaMinutos.add("05");
        listaMinutos.add("06");
        listaMinutos.add("07");
        listaMinutos.add("08");
        listaMinutos.add("09");
        listaMinutos.add("10");
        listaMinutos.add("11");
        listaMinutos.add("12");
        listaMinutos.add("13");
        listaMinutos.add("14");
        listaMinutos.add("15");
        listaMinutos.add("16");
        listaMinutos.add("17");
        listaMinutos.add("18");
        listaMinutos.add("19");
        listaMinutos.add("20");
        listaMinutos.add("21");
        listaMinutos.add("22");
        listaMinutos.add("23");
        listaMinutos.add("24");
        listaMinutos.add("25");
        listaMinutos.add("26");
        listaMinutos.add("27");
        listaMinutos.add("28");
        listaMinutos.add("29");
        listaMinutos.add("30");
        listaMinutos.add("31");
        listaMinutos.add("32");
        listaMinutos.add("33");
        listaMinutos.add("34");
        listaMinutos.add("35");
        listaMinutos.add("36");
        listaMinutos.add("37");
        listaMinutos.add("38");
        listaMinutos.add("39");
        listaMinutos.add("40");
        listaMinutos.add("41");
        listaMinutos.add("42");
        listaMinutos.add("43");
        listaMinutos.add("44");
        listaMinutos.add("45");
        listaMinutos.add("46");
        listaMinutos.add("47");
        listaMinutos.add("48");
        listaMinutos.add("49");
        listaMinutos.add("50");
        listaMinutos.add("51");
        listaMinutos.add("52");
        listaMinutos.add("53");
        listaMinutos.add("54");
        listaMinutos.add("55");
        listaMinutos.add("56");
        listaMinutos.add("57");
        listaMinutos.add("58");
        listaMinutos.add("59");

        return listaMinutos;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }

    public String prepararCreacion() {
        vueloActual = new Vuelo();
        codISOLocalidad = null; 
        hora = null; 
        minuto = null;

        return "Crear";
    }

    public String crear() {
        try {
            vueloActual.setAerolinea(getGestorVuelos().getAerolinea(codAerolinea));
            vueloActual.setLocalidad(getGestorVuelos().getLocalidad(codISOLocalidad));
            vueloActual.setHora(getHoraVuelo(hora, minuto));
            getGestorVuelos().crear(vueloActual);
            refrescarVuelos();
            vueloActual = new Vuelo();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroGuardado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);

            return null;
        }
    }

    public String prepararEditar() {
        DateFormat formateador;
        String horaMinuto;
        StringTokenizer tokenizer;

        vueloActual = getVuelos().getRowData();
        codAerolinea = vueloActual.getAerolinea().getCodAerolinea();
        codISOLocalidad = vueloActual.getLocalidad().getCodISOLocalidad();

        formateador = DateFormat.getTimeInstance(DateFormat.SHORT);
        horaMinuto = formateador.format(vueloActual.getHora());
        tokenizer = new StringTokenizer(horaMinuto, ":");
        hora = tokenizer.nextToken();
        minuto = tokenizer.nextToken();

        return "Editar";
    }

    public String actualizar() {
        try {
            vueloActual.setAerolinea(getGestorVuelos().getAerolinea(codAerolinea));
            vueloActual.setLocalidad(getGestorVuelos().getLocalidad(codISOLocalidad));
            vueloActual.setHora(getHoraVuelo(hora, minuto));
            getGestorVuelos().actualizar(vueloActual);
            refrescarVuelos();

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
            vueloActual = getVuelos().getRowData();
            getGestorVuelos().borrar(vueloActual);
            refrescarVuelos();

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("registroBorrado"));

            return "Lista";
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private void refrescarVuelos() {
        vuelos = new ListDataModel<Vuelo>(getGestorVuelos().filtrarPorAerolinea(codAerolinea));
    }

    private Date getHoraVuelo(String hora, String minuto) {
        DateFormat analizador;
        Date horaMinuto = null;

        analizador = DateFormat.getTimeInstance(DateFormat.SHORT);
        try {
            horaMinuto = analizador.parse(hora + ":" + minuto);
        } catch (ParseException ex) {
            getBitacora().log(Level.SEVERE, null, ex);
        }

        return horaMinuto;
    }
}
