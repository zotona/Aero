package com.wordpress.fcosfc.aero.factu.persistencia;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Entidad que representa a una línea de una factura
 * 
 * Se incluyen anotaciones que controlan la serialización XML y otras para la
 * validación de datos en las vistas (Bean Validation)
 * 
 * @author fsaucedo
 */
@Entity
@Table(name = "AEROFACTU_LINEAS_FACTURAS")
@SequenceGenerator(name="AEROFACTU_LINEAS_FACTURAS_S", initialValue=1, allocationSize=20)
@XmlType(propOrder={"idLineaFactura", "concepto", "unidades", "importeUnidad"})
public class LineaFactura implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="AEROFACTU_LINEAS_FACTURAS_S")
    @Column(name = "ID_LINEA_FACTURA")
    private Long idLineaFactura;
    
    @Column(name = "CONCEPTO", nullable = false)
    private String concepto;
    
    @Column(name = "UNIDADES", nullable = false, precision = 7, scale = 2)
    private Integer unidades;
    
    @Column(name = "IMPORTE_UNIDAD", nullable = false, precision = 7, scale = 2)
    private Double importeUnidad;
    
    @JoinColumn(name = "NUM_FACTURA", referencedColumnName = "NUM_FACTURA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Factura factura;

    public LineaFactura() {
    }

    public LineaFactura(String concepto, Integer unidades, Double importeUnidad, Factura factura) {
        this.concepto = concepto;
        this.unidades = unidades;
        this.importeUnidad = importeUnidad;
        this.factura = factura;
    }

    public Long getIdLineaFactura() {
        return idLineaFactura;
    }

    public void setIdLineaFactura(Long idLineaFactura) {
        this.idLineaFactura = idLineaFactura;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Double getImporteUnidad() {
        return importeUnidad;
    }

    public void setImporteUnidad(Double importeUnidad) {
        this.importeUnidad = importeUnidad;
    }

    public Double getImporteTotal() {
        return unidades * importeUnidad;
    }
    
    @XmlTransient
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }    

    @Override
    public String toString() {
        return "LineaFactura{" + "idLineaFactura=" + idLineaFactura + ", concepto=" + concepto + ", unidades=" + unidades + ", importeUnidad=" + importeUnidad + ", factura=" + factura + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.idLineaFactura != null ? this.idLineaFactura.hashCode() : 0);
        hash = 37 * hash + (this.concepto != null ? this.concepto.hashCode() : 0);
        hash = 37 * hash + (this.unidades != null ? this.unidades.hashCode() : 0);
        hash = 37 * hash + (this.importeUnidad != null ? this.importeUnidad.hashCode() : 0);
        hash = 37 * hash + (this.factura != null ? this.factura.hashCode() : 0);
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
        final LineaFactura other = (LineaFactura) obj;
        if (this.idLineaFactura != other.idLineaFactura && (this.idLineaFactura == null || !this.idLineaFactura.equals(other.idLineaFactura))) {
            return false;
        }
        if ((this.concepto == null) ? (other.concepto != null) : !this.concepto.equals(other.concepto)) {
            return false;
        }
        if (this.unidades != other.unidades && (this.unidades == null || !this.unidades.equals(other.unidades))) {
            return false;
        }
        if (this.importeUnidad != other.importeUnidad && (this.importeUnidad == null || !this.importeUnidad.equals(other.importeUnidad))) {
            return false;
        }
        if (this.factura != other.factura && (this.factura == null || !this.factura.equals(other.factura))) {
            return false;
        }
        return true;
    }
        
}
