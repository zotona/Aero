package com.wordpress.fcosfc.aero.gest.persistencia;

import java.io.Serializable;
import java.util.List;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Entidad que representa a una localidad de un país
 * 
 * Se incluyen anotaciones que controlan la serialización XML y otras para la
 * validación de datos en las vistas (Bean Validation)
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROGEST_LOCALIDADES")
@NamedQueries({
    @NamedQuery(name = "Localidad.FindAll", query = "SELECT l FROM Localidad l ORDER BY l.nombre"),
    @NamedQuery(name = "Localidad.FindByFiltroPorCodISOPaisYNombre", query = "SELECT l FROM Localidad l WHERE l.pais.codISOPais = :codISOPais AND upper(l.nombre) like(:filtro) ORDER BY l.nombre")})
@XmlType(propOrder = {"codISOLocalidad", "nombre", "pais"})
public class Localidad implements Serializable {
    
    @Id
    @Column(name = "COD_ISO_LOCALIDAD", length = 5)
    @NotNull
    @Size(min = 5, max = 5)
    private String codISOLocalidad;
    
    @Column(name = "NOMBRE", length = 100, nullable = false)
    @NotNull
    @Size(min = 2, max = 100)
    private String nombre;
    
    @JoinColumn(name = "COD_ISO_PAIS", referencedColumnName = "COD_ISO_PAIS")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pais pais;
    
    @OneToMany(mappedBy = "localidad", fetch = FetchType.LAZY)    
    private List<Vuelo> listaVuelos;

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

    @XmlTransient
    public List<Vuelo> getListaVuelos() {
        return listaVuelos;
    }

    public void setListaVuelos(List<Vuelo> listaVuelos) {
        this.listaVuelos = listaVuelos;
    }

    @Override
    public String toString() {
        return "Localidad{" + "codISOLocalidad=" + codISOLocalidad + ", nombre=" + nombre + ", pais=" + pais + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.codISOLocalidad != null ? this.codISOLocalidad.hashCode() : 0);
        hash = 67 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 67 * hash + (this.pais != null ? this.pais.hashCode() : 0);
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
        final Localidad other = (Localidad) obj;
        if ((this.codISOLocalidad == null) ? (other.codISOLocalidad != null) : !this.codISOLocalidad.equals(other.codISOLocalidad)) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if (this.pais != other.pais && (this.pais == null || !this.pais.equals(other.pais))) {
            return false;
        }
        return true;
    }
            
}
