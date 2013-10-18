package com.wordpress.fcosfc.aero.bean;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Clase abstracta con elementos de soporte para beans manejados por el servidor
 * de aplicaciones
 * 
 * @author fsaucedo
 */
public abstract class BeanAbstracto {
    
    protected abstract Logger getBitacora();
    
    @PostConstruct
    protected void inicializar() {
        getBitacora().log(Level.FINE, "Bean {0} {1} creado", new Object[]{this.getClass().getName(), this.hashCode()});
    }

    @PreDestroy
    protected void finalizar() {
        getBitacora().log(Level.FINE, "Bean {0} {1} destruido", new Object[]{this.getClass().getName(), this.hashCode()});
    }
}
