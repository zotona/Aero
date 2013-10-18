package com.wordpress.fcosfc.aero.gest.persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Entidad que representa una operación de despegue o aterrizaje en un aeropuerto
 * 
 * Se incluyen anotaciones que controlan la serialización XML y otras para la
 * validación de datos en las vistas (Bean Validation)
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROGEST_OPERACIONES")
@NamedQueries({
    @NamedQuery(name = "Operacion.FindAll", query = "SELECT o FROM Operacion o ORDER BY o.idOperacion DESC"),
    @NamedQuery(name = "Operacion.FindByFOperacion", query = "SELECT o FROM Operacion o WHERE o.fOperacion BETWEEN :fInicio AND :fFin ORDER BY o.fOperacion DESC")})
@SequenceGenerator(name="AEROGEST_OPERACIONES_S", initialValue=1, allocationSize=20)
@XmlRootElement
@XmlType(propOrder={"idOperacion", "FOperacion", "vuelo", "numPasajeros"})
public class Operacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="AEROGEST_OPERACIONES_S")
    @Column(name = "ID_OPERACION")
    private Long idOperacion;    
    
    @JoinColumn(name = "COD_VUELO", referencedColumnName = "COD_VUELO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Vuelo vuelo;
    
    @Column(name = "F_OPERACION", nullable = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fOperacion;
    
    @Column(name = "NUM_PASAJEROS", nullable = false, precision = 3, scale = 0)
    @NotNull
    @Digits(integer = 3, fraction = 0)
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

    @Override
    public String toString() {
        return "Operacion{" + "idOperacion=" + idOperacion + ", vuelo=" + vuelo + ", fOperacion=" + fOperacion + ", numPasajeros=" + numPasajeros + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.idOperacion != null ? this.idOperacion.hashCode() : 0);
        hash = 29 * hash + (this.vuelo != null ? this.vuelo.hashCode() : 0);
        hash = 29 * hash + (this.fOperacion != null ? this.fOperacion.hashCode() : 0);
        hash = 29 * hash + (this.numPasajeros != null ? this.numPasajeros.hashCode() : 0);
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
        final Operacion other = (Operacion) obj;
        if (this.idOperacion != other.idOperacion && (this.idOperacion == null || !this.idOperacion.equals(other.idOperacion))) {
            return false;
        }
        if (this.vuelo != other.vuelo && (this.vuelo == null || !this.vuelo.equals(other.vuelo))) {
            return false;
        }
        if (this.fOperacion != other.fOperacion && (this.fOperacion == null || !this.fOperacion.equals(other.fOperacion))) {
            return false;
        }
        if (this.numPasajeros != other.numPasajeros && (this.numPasajeros == null || !this.numPasajeros.equals(other.numPasajeros))) {
            return false;
        }
        return true;
    }
        
}
