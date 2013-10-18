package com.wordpress.fcosfc.aero.control;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.persistencia.AbstractCrudService;

/**
 * Clase abstracta con los métodos básicos de acceso a la capa de persistencia
 * 
 * @author fsaucedo
 */
public abstract class GestorAbstracto<T> extends BeanAbstracto {
    
    private Class<T> claseEntidad;
    
    public GestorAbstracto(Class<T> claseEntidad) {
        this.claseEntidad = claseEntidad;
    }
    
    protected abstract AbstractCrudService getCrudService();    
    
    public void crear(T entidad) {
        getCrudService().create(entidad);
    }
    
    public void actualizar(T entidad) {
        getCrudService().update(entidad);
    }
    
    public void borrar(T entidad) {
        getCrudService().delete(entidad);
    }
    
    public T buscar(Object id) {
        return getCrudService().find(claseEntidad, id);
    }
}
