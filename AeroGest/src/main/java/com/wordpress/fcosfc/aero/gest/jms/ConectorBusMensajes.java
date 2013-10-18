package com.wordpress.fcosfc.aero.gest.jms;

import com.wordpress.fcosfc.aero.bean.BeanAbstracto;
import com.wordpress.fcosfc.aero.gest.persistencia.Operacion;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * Clase que permite enviar mensajes JMS
 * 
 * @author fsaucedo
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ConectorBusMensajes extends BeanAbstracto {

    private static final Logger bitacora = Logger.getLogger(ConectorBusMensajes.class.getName());
    
    private static final String UTF8 = "UTF8";
    
    @Resource(mappedName = "jms/Aero/XAConnectionFactory")
    ConnectionFactory factoriaConexiones;
    
    @Resource(mappedName = "jms/Aero/OperacionesAereas")
    Topic operacionesAereas;
    
    Connection conexionJMS;
    Session sesionJMS;
    MessageProducer productorMensajes;

    public ConectorBusMensajes() {
        super();
        conexionJMS = null;
        sesionJMS = null;
        productorMensajes = null;
    }

    @PostConstruct
    @Override
    protected void inicializar() {
        if (factoriaConexiones == null || operacionesAereas == null) {
            getBitacora().log(Level.SEVERE, "AeroGest --> ConectorBusMensajes.inicializar: no se han podido obtener los recursos JMS necesarios");
        } else {
            try {
                conexionJMS = factoriaConexiones.createConnection();
                sesionJMS = conexionJMS.createSession(true, Session.SESSION_TRANSACTED);
                productorMensajes = sesionJMS.createProducer(operacionesAereas);

                getBitacora().log(Level.FINE, "Bean {0} {1} creado", new Object[]{this.getClass().getName(), this.hashCode()});
            } catch (JMSException ex) {
                getBitacora().log(Level.SEVERE, null, ex);
            }

        }
    }

    @PreDestroy
    @Override
    protected void finalizar() {
        if (productorMensajes != null) {
            try {
                productorMensajes.close();
            } catch (JMSException ex) {
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }
        if (sesionJMS != null) {
            try {
                sesionJMS.close();
            } catch (JMSException ex) {
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }
        if (conexionJMS != null) {
            try {
                conexionJMS.close();
            } catch (JMSException ex) {
                getBitacora().log(Level.SEVERE, null, ex);
            }
        }
        getBitacora().log(Level.FINE, "Bean {0} {1} destruido", new Object[]{this.getClass().getName(), this.hashCode()});
    }

    @Override
    protected Logger getBitacora() {
        return bitacora;
    }

    /**
     * Método que envía un mensaje comunicando el registro de una operación aérea
     * 
     * @param operacion 
     */
    public void enviarMensaje(Operacion operacion) {
        JAXBContext jaxbContext;
        Marshaller marshaller;
        ByteArrayOutputStream baos;
        TextMessage mensaje;
        String msg;

        try {
            jaxbContext = JAXBContext.newInstance(com.wordpress.fcosfc.aero.gest.persistencia.Operacion.class);
            marshaller = jaxbContext.createMarshaller();
            baos = new ByteArrayOutputStream();
            marshaller.marshal(operacion, baos);
            msg = baos.toString(UTF8);

            mensaje = sesionJMS.createTextMessage(msg);
            productorMensajes.send(mensaje);
            
            getBitacora().log(Level.INFO, "AeroGest --> ConectorBusMensajes.enviarMensaje: mensaje {0} enviado", msg);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
        
}
