package com.wordpress.fcosfc.aero.xml;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlType;

/**
 * Bean que representa una aerolínea
 *
 * Se incluyen anotaciones que controlan la deserialización XML
 * 
 * @author fsaucedo
 */
@XmlType(propOrder = {"codAerolinea", "nombre"})
public class Aerolinea implements Serializable {

    private String codAerolinea;
    private String nombre;
    
    public String getCodAerolinea() {
        return codAerolinea;
    }

    public void setCodAerolinea(String codAerolinea) {
        this.codAerolinea = codAerolinea;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
