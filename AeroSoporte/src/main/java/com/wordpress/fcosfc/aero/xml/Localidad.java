package com.wordpress.fcosfc.aero.xml;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlType;

/**
 * Bean que representa una localidad
 *
 * Se incluyen anotaciones que controlan la deserialización XML
 * 
 * @author fsaucedo
 */
@XmlType(propOrder = {"codISOLocalidad", "nombre", "pais"})
public class Localidad implements Serializable {
    
    private String codISOLocalidad;
    private String nombre;
    private Pais pais;
    
    public String getCodISOLocalidad() {
        return codISOLocalidad;
    }
    
    public void setCodISOLocalidad(String codISOLocalidad) {
        this.codISOLocalidad = codISOLocalidad;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Pais getPais() {
        return pais;
    }
    
    public void setPais(Pais pais) {
        this.pais = pais;
    }    
}
