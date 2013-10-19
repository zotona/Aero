Aero
====

Conjunto de aplicaciones Java EE con un ejemplo práctico de uso de JMS

El proyecto ha sido creado para ser presentado como apoyo a una charla sobre JMS en el Betabeers Estrecho de Gibraltar. Se compone de cuatro subproyectos:

* AeroSoporte, que contiene código usado por los otros tres subproyectos.
* AeroGest, que es una pequeña aplicación para una gestión básica de las operaciones aéreas de un aeropuerto.
* AeroFactu, que es un programa de facturación de las operaciones registradas en AeroGest.
* AeroEstad, que proporciona estadísticas básicas que tienen como base las operaciones grabadas en AeroGest.

Ha sido desarrollado usando NetBeans 7.3 (es un proyecto maven, lo que implica que se pueden usar otros entornos de desarollo compatibles con maven). Se ha probado desplegando los proyectos AeroGest, AeroFactu y AeroEstad en el servidor Glassfish 3.1.2.2, que viene con la instalación de NetBeans 7.3

Antes de despleglar el proyecto se tienen que crear en el servidor de aplicaciones los siguientes recursos:

* Fuente de datos XA (javax.sql.XADataSource): jdbc/Aero
* Factoría de conexiones JMS XA: jms/Aero/XAConnectionFactory
* Topic: jms/Aero/OperacionesAereas
* Topic: jms/Aero/Facturas

El fichero recursos-glassfish.txt contiene las sentencias necesarias para crear los recursos anteriores en un servidor Glassfish. Para ello se tiene que ejecutar el siguiente comando:

$GLASSFISH_HOME/bin/asadmin multimode --file recursos-glassfish.txt
