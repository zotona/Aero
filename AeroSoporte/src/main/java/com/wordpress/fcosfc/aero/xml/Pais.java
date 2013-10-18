package com.wordpress.fcosfc.aero.xml;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlType;

/**
 * Bean que representa un país
 *
 * Se incluyen anotaciones que controlan la deserialización XML
 * 
 * @author fsaucedo
 */
@XmlType(propOrder = {"codISOPais", "nombre"})
public class Pais implements Serializable {
    
    private String codISOPais;
    private String nombre;
    
    public String getCodISOPais() {
        return codISOPais;
    }

    public void setCodISOPais(String codISOPais) {
        this.codISOPais = codISOPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
