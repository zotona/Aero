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
 * Entidad que representa a un país
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROESTAD_PAISES")
public class Pais implements Serializable {
        
    @Id
    @Column(name = "COD_ISO_PAIS", length = 2)
    private String codISOPais;
    
    @Column(name = "NOMBRE", length = 100, nullable = false)
    private String nombre;
    
    @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)    
    private List<Operacion> listaOperaciones;

    public Pais() {
    }

    public Pais(String codISOPais, String nombre) {
        this.codISOPais = codISOPais;
        this.nombre = nombre;
    }
        
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

    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<Operacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    @Override
    public String toString() {
        return "Pais{" + "codISOPais=" + codISOPais + ", nombre=" + nombre + '}';
    }       

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (this.codISOPais != null ? this.codISOPais.hashCode() : 0);
        hash = 29 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
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
        final Pais other = (Pais) obj;
        if ((this.codISOPais == null) ? (other.codISOPais != null) : !this.codISOPais.equals(other.codISOPais)) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }
        
}
