package com.wordpress.fcosfc.aero.factu.persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Entidad que representa a un cliente
 * 
 * Se incluyen anotaciones que controlan la serialización XML y otras para la
 * validación de datos en las vistas (Bean Validation)
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROFACTU_CLIENTES")
@XmlType(propOrder={"codCliente", "nombre"})
public class Cliente implements Serializable {
    
    @Id
    @Column(name = "COD_CLIENTE", length = 3)
    private String codCliente;
    
    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Factura> facturas;

    public Cliente() {
    }

    public Cliente(String codCliente, String nombre) {
        this.codCliente = codCliente;
        this.nombre = nombre;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }        

    @XmlTransient
    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }   

    @Override
    public String toString() {
        return "Cliente{" + "codCliente=" + codCliente + ", nombre=" + nombre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.codCliente != null ? this.codCliente.hashCode() : 0);
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
        final Cliente other = (Cliente) obj;
        if ((this.codCliente == null) ? (other.codCliente != null) : !this.codCliente.equals(other.codCliente)) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }
    
}
