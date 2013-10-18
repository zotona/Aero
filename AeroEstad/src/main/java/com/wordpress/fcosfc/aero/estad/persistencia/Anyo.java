package com.wordpress.fcosfc.aero.estad.persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad que representa a un año
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROESTAD_ANYOS")
@NamedQuery(name = "Anyo.FindAll", query = "SELECT a FROM Anyo a ORDER BY a.anyo DESC")
public class Anyo implements Serializable {
    
    @Id
    @Column(name = "ANYO", precision = 4, scale = 0)
    private Integer anyo;
    
    @OneToMany(mappedBy = "anyo", fetch = FetchType.LAZY)
    private List<Operacion> listaOperaciones;

    public Anyo() {
    }

    public Anyo(Integer anyo) {
        this.anyo = anyo;
    }
    
    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    public List<Operacion> getListaOperaciones() {
        return listaOperaciones;
    }

    public void setListaOperaciones(List<Operacion> listaOperaciones) {
        this.listaOperaciones = listaOperaciones;
    }

    @Override
    public String toString() {
        return anyo.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.anyo != null ? this.anyo.hashCode() : 0);
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
        final Anyo other = (Anyo) obj;
        if (this.anyo != other.anyo && (this.anyo == null || !this.anyo.equals(other.anyo))) {
            return false;
        }
        return true;
    }
        
}
