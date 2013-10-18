package com.wordpress.fcosfc.aero.gest.persistencia;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Entidad que representa a un vuelo de una aerolínea
 * 
 * Se incluyen anotaciones que controlan la serialización XML y otras para la
 * validación de datos en las vistas (Bean Validation)
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROGEST_VUELOS")
@NamedQueries({
    @NamedQuery(name = "Vuelo.FindByAerolinea", query = "SELECT v FROM Vuelo v WHERE v.aerolinea.codAerolinea = :codAerolinea ORDER BY v.localidad.codISOLocalidad, v.hora"),
    @NamedQuery(name = "Vuelo.FindByAerolineaYEsSalida", query = "SELECT v FROM Vuelo v WHERE v.aerolinea.codAerolinea = :codAerolinea AND v.esSalida = :esSalida ORDER BY v.localidad.codISOLocalidad, v.hora")})
@XmlType(propOrder = {"codVuelo", "aerolinea", "esSalida", "horaPrevista", "localidad"})
public class Vuelo implements Serializable {

    @Id
    @Column(name = "COD_VUELO", length = 6)
    @NotNull
    @Size(min = 6, max = 6)
    private String codVuelo;
    
    @JoinColumn(name = "COD_ISO_LOCALIDAD", referencedColumnName = "COD_ISO_LOCALIDAD")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Localidad localidad;
    
    @Column(name = "ES_SALIDA", nullable = false)
    @NotNull
    private boolean esSalida;
    
    @Column(name = "HORA")
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date hora;
    
    @JoinColumn(name = "COD_AEROLINEA", referencedColumnName = "COD_AEROLINEA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Aerolinea aerolinea;
    
    @OneToMany(mappedBy = "vuelo", fetch = FetchType.LAZY)
    private List<Operacion> listaOperaciones;

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

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    @XmlTransient
    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<Operacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    @Override
    public String toString() {
        return "Vuelo{" + "codVuelo=" + codVuelo + ", localidad=" + localidad + ", esSalida=" + esSalida + ", hora=" + hora + ", aerolinea=" + aerolinea + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.codVuelo != null ? this.codVuelo.hashCode() : 0);
        hash = 53 * hash + (this.localidad != null ? this.localidad.hashCode() : 0);
        hash = 53 * hash + (this.esSalida ? 1 : 0);
        hash = 53 * hash + (this.hora != null ? this.hora.hashCode() : 0);
        hash = 53 * hash + (this.aerolinea != null ? this.aerolinea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vuelo other = (Vuelo) obj;
        if ((this.codVuelo == null) ? (other.codVuelo != null) : !this.codVuelo.equals(other.codVuelo)) {
            return false;
        }
        if (this.localidad != other.localidad && (this.localidad == null || !this.localidad.equals(other.localidad))) {
            return false;
        }
        if (this.esSalida != other.esSalida) {
            return false;
        }
        if (this.hora != other.hora && (this.hora == null || !this.hora.equals(other.hora))) {
            return false;
        }
        if (this.aerolinea != other.aerolinea && (this.aerolinea == null || !this.aerolinea.equals(other.aerolinea))) {
            return false;
        }
        return true;
    }
    
}
