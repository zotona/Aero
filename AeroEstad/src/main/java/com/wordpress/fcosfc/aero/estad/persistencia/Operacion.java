package com.wordpress.fcosfc.aero.estad.persistencia;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entidad que representa un registro estadístico de operación aérea
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROESTAD_OPERACIONES")
@NamedQuery(name = "Operacion.FindByAnyo", query = "SELECT o.mes "
                                                 + "     , o.aerolinea.nombre "
                                                 + "     , o.pais.nombre "
                                                 + "     , o.esSalida "
                                                 + "     , SUM(o.numPasajeros) "
                                                 + "FROM Operacion o "
                                                 + "WHERE o.anyo = :anyo "
                                                 + "GROUP BY o.mes "
                                                 + "       , o.aerolinea.nombre "
                                                 + "       , o.pais.nombre "
                                                 + "       , o.esSalida "
                                                 + "ORDER BY o.mes")
@SequenceGenerator(name="AEROESTAD_OPERACIONES_S", initialValue=1, allocationSize=20)
public class Operacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="AEROESTAD_OPERACIONES_S")
    @Column(name = "ID_OPERACION")
    private Long idOperacion;    
    
    @JoinColumn(name = "ANYO", referencedColumnName = "ANYO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Anyo anyo;
    
    @Column(name = "MES", precision = 2, scale = 0)
    private Integer mes;
    
    @JoinColumn(name = "COD_AEROLINEA", referencedColumnName = "COD_AEROLINEA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Aerolinea aerolinea;
    
    @JoinColumn(name = "COD_ISO_PAIS", referencedColumnName = "COD_ISO_PAIS")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pais pais;
    
    @Column(name = "ES_SALIDA", nullable = false)
    private boolean esSalida;
    
    @Column(name = "NUM_PASAJEROS", nullable = false, precision = 3, scale = 0)
    private Integer numPasajeros;

    public Operacion() {
    }

    public Operacion(Anyo anyo, Integer mes, Aerolinea aerolinea, Pais pais, boolean esSalida, Integer numPasajeros) {
        this.anyo = anyo;
        this.mes = mes;
        this.aerolinea = aerolinea;
        this.pais = pais;
        this.esSalida = esSalida;
        this.numPasajeros = numPasajeros;
    }

    public Long getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Long idOperacion) {
        this.idOperacion = idOperacion;
    }

    public Anyo getAnyo() {
        return anyo;
    }

    public void setAnyo(Anyo anyo) {
        this.anyo = anyo;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public boolean isEsSalida() {
        return esSalida;
    }

    public void setEsSalida(boolean esSalida) {
        this.esSalida = esSalida;
    }

    public Integer getNumPasajeros() {
        return numPasajeros;
    }

    public void setNumPasajeros(Integer numPasajeros) {
        this.numPasajeros = numPasajeros;
    }

    @Override
    public String toString() {
        return "Operacion{" + "idOperacion=" + idOperacion + ", anyo=" + anyo + ", mes=" + mes + ", aerolinea=" + aerolinea + ", pais=" + pais + ", esSalida=" + esSalida + ", numPasajeros=" + numPasajeros + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.idOperacion != null ? this.idOperacion.hashCode() : 0);
        hash = 59 * hash + (this.anyo != null ? this.anyo.hashCode() : 0);
        hash = 59 * hash + (this.mes != null ? this.mes.hashCode() : 0);
        hash = 59 * hash + (this.aerolinea != null ? this.aerolinea.hashCode() : 0);
        hash = 59 * hash + (this.pais != null ? this.pais.hashCode() : 0);
        hash = 59 * hash + (this.esSalida ? 1 : 0);
        hash = 59 * hash + (this.numPasajeros != null ? this.numPasajeros.hashCode() : 0);
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
        if (this.anyo != other.anyo && (this.anyo == null || !this.anyo.equals(other.anyo))) {
            return false;
        }
        if (this.mes != other.mes && (this.mes == null || !this.mes.equals(other.mes))) {
            return false;
        }
        if (this.aerolinea != other.aerolinea && (this.aerolinea == null || !this.aerolinea.equals(other.aerolinea))) {
            return false;
        }
        if (this.pais != other.pais && (this.pais == null || !this.pais.equals(other.pais))) {
            return false;
        }
        if (this.esSalida != other.esSalida) {
            return false;
        }
        if (this.numPasajeros != other.numPasajeros && (this.numPasajeros == null || !this.numPasajeros.equals(other.numPasajeros))) {
            return false;
        }
        return true;
    }
        
}
