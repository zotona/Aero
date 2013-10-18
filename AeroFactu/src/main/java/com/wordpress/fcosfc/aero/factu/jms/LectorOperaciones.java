package com.wordpress.fcosfc.aero.factu.jms;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.factu.control.Facturador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Bean que lee operaciones aéreas y las factura
 * 
 * @author fsaucedo
 */
@MessageDriven(mappedName = "jms/Aero/OperacionesAereas",
        activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType",
            propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "AeroFactu"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "AeroFactu"),
    @ActivationConfigProperty(propertyName = "endpointExceptionRedeliveryAttempts", propertyValue = "3"),
    @ActivationConfigProperty(propertyName = "endpointExceptionRedeliveryInterval", propertyValue = "10")})
public class LectorOperaciones extends BeanAbstracto implements MessageListener {
       
    private static final Logger bitacora = Logger.getLogger(LectorOperaciones.class.getName());

    @Resource
    private MessageDrivenContext mdc;

    @Inject
    private Facturador facturador;
    
    public LectorOperaciones() { 
        super();
    }  

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }    

    @Override
    public void onMessage(Message message) {
        TextMessage msg;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                getBitacora().log(Level.INFO, "AeroFactu --> LectorOperaciones.onMessage: mensaje recibido {0}", msg.getText());                               
                
                facturador.facturar(msg);                
            } else {
                getBitacora().log(Level.WARNING, "AeroFactu --> LectorOperaciones.onMessage: mensaje recibido {0} de un tipo no esperado", message.getClass().getName());
            }
        } catch (JMSException ex) {
            getBitacora().log(Level.SEVERE, "AeroFactu --> LectorOperaciones.onMessage: excepción JMS", ex);
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            getBitacora().log(Level.SEVERE, "AeroFactu --> LectorOperaciones.onMessage: excepción", te);
            mdc.setRollbackOnly();
        }
    }
}
