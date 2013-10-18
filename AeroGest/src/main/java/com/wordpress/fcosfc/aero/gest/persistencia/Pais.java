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
 * Entidad que representa a un país
 * 
 * Se incluyen anotaciones que controlan la serialización XML y otras para la
 * validación de datos en las vistas (Bean Validation)
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROGEST_PAISES")
@NamedQueries({
    @NamedQuery(name = "Pais.FindAll", query = "SELECT p FROM Pais p ORDER BY p.nombre"),
    @NamedQuery(name = "Pais.FindByFiltroPorNombre", query = "SELECT p FROM Pais p WHERE upper(p.nombre) like(:filtro) ORDER BY p.nombre")})
@XmlType(propOrder = {"codISOPais", "nombre"})
public class Pais implements Serializable {
        
    @Id
    @Column(name = "COD_ISO_PAIS", length = 2)
    @NotNull
    @Size(min = 2, max = 2)
    private String codISOPais;
    
    @Column(name = "NOMBRE", length = 100, nullable = false)
    @NotNull
    @Size(min = 5, max = 100)
    private String nombre;
    
    @OneToMany(mappedBy = "pais", fetch = FetchType.LAZY)    
    private List<Localidad> listaLocalidades;
        
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
    
    @XmlTransient
    public List<Localidad> getListaLocalidades() {
        return listaLocalidades;
    }
    
    public void setListaLocalidades(List<Localidad> listaLocalidades) {
        this.listaLocalidades = listaLocalidades;
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
