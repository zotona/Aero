package com.wordpress.fcosfc.aero.gest.fachada.util;

import com.wordpress.fcosfc.aero.gest.persistencia.Operacion;
import com.wordpress.fcosfc.aero.gest.persistencia.Vuelo;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Clase de apoyo en la generación de la vista JSF de las operaciones aéreas
 * 
 * @author fsaucedo
 */
public class OperacionAerea {

    private Date fOperacion;
    private String vuelo;
    private Integer numPasajeros;

    public static List<OperacionAerea> getOperacionesAereas(List<Operacion> operaciones) {
        List<OperacionAerea> operacionesAereas = null;

        if (operaciones != null) {
            Vuelo vuelo;
            String datosVuelo;
            DateFormat timeFormat;

            operacionesAereas = new ArrayList<OperacionAerea>(operaciones.size());
            timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);

            for (Operacion operacion : operaciones) {
                vuelo = operacion.getVuelo();
                datosVuelo = vuelo.isEsSalida() ? ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("vueloSalida") : ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/gest/recursos/etiquetas").getString("vueloLlegada");
                datosVuelo += ", " + vuelo.getAerolinea().getNombre();
                datosVuelo += ", " + vuelo.getLocalidad().getNombre();
                datosVuelo += ", " + timeFormat.format(vuelo.getHora());

                operacionesAereas.add(new OperacionAerea(operacion.getFOperacion(), datosVuelo, operacion.getNumPasajeros()));
            }
        }
        
        return operacionesAereas;
    }

    public OperacionAerea() {
    }

    public OperacionAerea(Date fOperacion, String vuelo, Integer numPasajeros) {
        this.fOperacion = fOperacion;
        this.vuelo = vuelo;
        this.numPasajeros = numPasajeros;
    }

    public Date getfOperacion() {
        return fOperacion;
    }

    public void setfOperacion(Date fOperacion) {
        this.fOperacion = fOperacion;
    }

    public String getVuelo() {
        return vuelo;
    }

    public void setVuelo(String vuelo) {
        this.vuelo = vuelo;
    }

    public Integer getNumPasajeros() {
        return numPasajeros;
    }

    public void setNumPasajeros(Integer numPasajeros) {
        this.numPasajeros = numPasajeros;
    }
}
