package com.wordpress.fcosfc.aero.xml;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Bean que representa un vuelo
 *
 * Se incluyen anotaciones que controlan la deserialización XML
 * 
 * @author fsaucedo
 */
@XmlType(propOrder = {"codVuelo", "aerolinea", "esSalida", "horaPrevista", "localidad"})
public class Vuelo implements Serializable {

    private String codVuelo;
    private Localidad localidad;
    private boolean esSalida;
    private Date hora;
    private Aerolinea aerolinea;
    
    public String getCodVuelo() {
        return codVuelo;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public boolean isEsSalida() {
        return esSalida;
    }

    public void setEsSalida(boolean esSalida) {
        this.esSalida = esSalida;
    }

    @XmlTransient
    public Date getHora() {
        return hora;
    }

    @XmlSchemaType(name = "time")
    public XMLGregorianCalendar getHoraPrevista() {
        GregorianCalendar gregorianCalendar;
        DatatypeFactory dataTypeFactory = null;
        
        gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        gregorianCalendar.setTime(hora);
        try {
            dataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(Vuelo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataTypeFactory.newXMLGregorianCalendar(gregorianCalendar);
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
    
    public void setHoraPrevista(XMLGregorianCalendar horaPrevista) {
        this.hora = horaPrevista.toGregorianCalendar().getTime();
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }
}
