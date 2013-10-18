package com.wordpress.fcosfc.aero.estad.fachada.util;

/**
 * Bean auxiliar que representa a una línea de un listado estadístico
 * 
 * @author fsaucedo
 */
public class LineaListadoEstadistica {
    
    private String mes, aerolinea, pais, tipoOperacion;
    private Long numPasajeros;

    public LineaListadoEstadistica() {
    }

    public LineaListadoEstadistica(String mes, String aerolinea, String pais, String tipoOperacion, Long numPasajeros) {
        this.mes = mes;
        this.aerolinea = aerolinea;
        this.pais = pais;
        this.tipoOperacion = tipoOperacion;
        this.numPasajeros = numPasajeros;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public Long getNumPasajeros() {
        return numPasajeros;
    }

    public void setNumPasajeros(Long numPasajeros) {
        this.numPasajeros = numPasajeros;
    }
        
}
