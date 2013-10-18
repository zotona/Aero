package com.wordpress.fcosfc.aero.factu.persistencia;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Entidad que representa a una factura
 * 
 * Se incluyen anotaciones que controlan la serialización XML y otras para la
 * validación de datos en las vistas (Bean Validation)
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROFACTU_FACTURAS")
@NamedQuery(name = "Factura.FindByFFactura", query = "SELECT f FROM Factura f WHERE f.fFactura BETWEEN :fInicio AND :fFin ORDER BY f.fFactura DESC")
@SequenceGenerator(name="AEROFACTU_FACTURAS_S", initialValue=1, allocationSize=20)
@XmlRootElement
@XmlType(propOrder={"numFactura", "fFactura", "cliente", "referencia", "importeTotal", "lineasFactura"})
public class Factura implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="AEROFACTU_FACTURAS_S")
    @Column(name = "NUM_FACTURA")
    private Long numFactura;
    
    @Column(name = "F_FACTURA", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)    
    private Date fFactura;
    
    @JoinColumn(name = "COD_CLIENTE", referencedColumnName = "COD_CLIENTE")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;
    
    @Column(name = "REFERENCIA", length = 100, nullable = false)
    private String referencia;
    
    @Column(name = "IMPORTE_TOTAL", precision = 7, scale = 2)
    private Double importeTotal;
    
    @OneToMany(mappedBy = "factura", fetch = FetchType.LAZY)
    private List<LineaFactura> lineasFactura;

    public Factura() {
    }

    public Factura(Date fFactura, Cliente cliente, String referencia, Double importeTotal) {
        this.fFactura = fFactura;
        this.cliente = cliente;
        this.referencia = referencia;
        this.importeTotal = importeTotal;
    }

    public Long getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(Long numFactura) {
        this.numFactura = numFactura;
    }

    public Date getfFactura() {
        return fFactura;
    }

    public void setfFactura(Date fFactura) {
        this.fFactura = fFactura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Double importeTotal) {
        this.importeTotal = importeTotal;
    }  

    public List<LineaFactura> getLineasFactura() {
        return lineasFactura;
    }

    public void setLineasFactura(List<LineaFactura> lineasFactura) {
        this.lineasFactura = lineasFactura;
    }    

    @Override
    public String toString() {
        return "Factura{" + "numFactura=" + numFactura + ", fFactura=" + fFactura + ", cliente=" + cliente + ", referencia=" + referencia + ", importeTotal=" + importeTotal + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.numFactura != null ? this.numFactura.hashCode() : 0);
        hash = 79 * hash + (this.fFactura != null ? this.fFactura.hashCode() : 0);
        hash = 79 * hash + (this.cliente != null ? this.cliente.hashCode() : 0);
        hash = 79 * hash + (this.referencia != null ? this.referencia.hashCode() : 0);
        hash = 79 * hash + (this.importeTotal != null ? this.importeTotal.hashCode() : 0);
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
        final Factura other = (Factura) obj;
        if (this.numFactura != other.numFactura && (this.numFactura == null || !this.numFactura.equals(other.numFactura))) {
            return false;
        }
        if (this.fFactura != other.fFactura && (this.fFactura == null || !this.fFactura.equals(other.fFactura))) {
            return false;
        }
        if (this.cliente != other.cliente && (this.cliente == null || !this.cliente.equals(other.cliente))) {
            return false;
        }
        if ((this.referencia == null) ? (other.referencia != null) : !this.referencia.equals(other.referencia)) {
            return false;
        }
        if (this.importeTotal != other.importeTotal && (this.importeTotal == null || !this.importeTotal.equals(other.importeTotal))) {
            return false;
        }
        return true;
    }
    
}
