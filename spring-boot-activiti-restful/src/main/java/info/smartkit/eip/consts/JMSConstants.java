package info.smartkit.eip.consts;

import org.apache.activemq.ActiveMQConnection;

public class JMSConstants {
	final static public String JMS_DESTINATION_ACTIVEMQ = "message-destination-activemq";
	final static public String LOG_FILE_NAME_ACTIVEMQ = "file-data-activemq";
	static public String URL_BROKER_ACTIVEMQ = ActiveMQConnection.DEFAULT_BROKER_URL;//tcp://182.92.232.131:61616
}