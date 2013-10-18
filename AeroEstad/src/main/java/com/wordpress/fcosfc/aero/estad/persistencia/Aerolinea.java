package com.wordpress.fcosfc.aero.estad.persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad que representa a una aerolínea
 *
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROESTAD_AEROLINEAS")
public class Aerolinea implements Serializable {

    @Id
    @Column(name = "COD_AEROLINEA", length = 3)
    private String codAerolinea;
    
    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;
    
    @OneToMany(mappedBy = "aerolinea", fetch = FetchType.LAZY)
    private List<Operacion> listaOperaciones;

    public Aerolinea() {
    }

    public Aerolinea(String codAerolinea, String nombre) {
        this.codAerolinea = codAerolinea;
        this.nombre = nombre;
    }
    
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

    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<Operacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
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
