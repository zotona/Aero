package com.wordpress.fcosfc.aero.xml;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Bean que representa una operación aérea
 *
 * Se incluyen anotaciones que controlan la deserialización XML
 * 
 * @author fsaucedo
 */
@XmlRootElement
@XmlType(propOrder={"idOperacion", "FOperacion", "vuelo", "numPasajeros"})
public class Operacion implements Serializable {

    private Long idOperacion;    
    private Vuelo vuelo;
    private Date fOperacion;
    private Integer numPasajeros;

    public Long getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Long idOperacion) {
        this.idOperacion = idOperacion;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Date getFOperacion() {
        return fOperacion;
    }

    public void setFOperacion(Date fOperacion) {
        this.fOperacion = fOperacion;
    }  
    
    public Integer getNumPasajeros() {
        return numPasajeros;
    }

    public void setNumPasajeros(Integer numPasajeros) {
        this.numPasajeros = numPasajeros;
    }
        
}
