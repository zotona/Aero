package com.wordpress.fcosfc.aero.gest.persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Entidad que representa a una aerolínea
 * 
 * Se incluyen anotaciones que controlan la serialización XML y otras para la
 * validación de datos en las vistas (Bean Validation)
 *
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROGEST_AEROLINEAS")
@NamedQueries({
    @NamedQuery(name = "Aerolinea.FindAll", query = "SELECT a FROM Aerolinea a ORDER BY a.nombre"),
    @NamedQuery(name = "Aerolinea.FindByFiltroPorNombre", query = "SELECT a FROM Aerolinea a WHERE upper(a.nombre) like(:filtro) ORDER BY a.nombre")})
@XmlType(propOrder = {"codAerolinea", "nombre"})
public class Aerolinea implements Serializable {

    @Id
    @Column(name = "COD_AEROLINEA", length = 3)
    @NotNull
    @Size(min = 3, max = 3)
    private String codAerolinea;
    
    @Column(name = "NOMBRE", length = 30, nullable = false)
    @NotNull
    @Size(min = 3, max = 30)
    private String nombre;
    
    @OneToMany(mappedBy = "aerolinea", fetch = FetchType.LAZY)
    private List<Vuelo> listaVuelos;

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

    @XmlTransient
    public List<Vuelo> getListaVuelos() {
        return listaVuelos;
    }

    public void setListaVuelos(List<Vuelo> listaVuelos) {
        this.listaVuelos = listaVuelos;
    }

    @Override
    public String toString() {
        return "Aerolinea{" + "codAerolinea=" + codAerolinea + ", nombre=" + nombre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.codAerolinea != null ? this.codAerolinea.hashCode() : 0);
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
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
        final Aerolinea other = (Aerolinea) obj;
        if ((this.codAerolinea == null) ? (other.codAerolinea != null) : !this.codAerolinea.equals(other.codAerolinea)) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }
    
}
