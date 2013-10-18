package com.wordpress.fcosfc.aero.estad.fachada;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.estad.control.GestorEstadisticas;
import com.wordpress.fcosfc.aero.estad.persistencia.Anyo;
import com.wordpress.fcosfc.aero.fachada.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Bean fachada de la vista con el listado estadístico
 * 
 * @author fsaucedo
 */
@Named
@SessionScoped
public class Estadistica extends BeanAbstracto implements Serializable {
       
    private static final Logger bitacora = Logger.getLogger(Estadistica.class.getName());
    
    @Inject
    private GestorEstadisticas gestorEstadisticas;
    
    private DataModel listado;
    private List<Anyo> anyos;
    private Integer anyo;
    
    public Estadistica() {
        super();
    }
    
    @PostConstruct
    @Override
    protected void inicializar() {
        try {
            anyos = getGestorEstadisticas().getAnyos();
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/estad/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
        }
        
        getBitacora().log(Level.FINE, "Bean {0} {1} creado", new Object[]{this.getClass().getName(), this.hashCode()});
    }  

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }  
    
    protected GestorEstadisticas getGestorEstadisticas() {
        return gestorEstadisticas;
    }

    public DataModel getListado() {
        return listado;
    }

    public void setListado(DataModel listado) {
        this.listado = listado;
    }

    public List<Anyo> getAnyos() {
        return anyos;
    }

    public void setAnyos(List<Anyo> anyos) {
        this.anyos = anyos;
    }

    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }
        
    public void refrescar() {
        try {
            if (anyo != null) {
                listado = new ListDataModel(getGestorEstadisticas().getListado(anyo));
                anyos = getGestorEstadisticas().getAnyos();
            }
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/com/wordpress/fcosfc/aero/estad/recursos/etiquetas").getString("errorAccesoADatosDetectado"));
            getBitacora().log(Level.SEVERE, null, ex);
        }
    }
}
