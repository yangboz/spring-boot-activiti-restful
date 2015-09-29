package info.smartkit.eip.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@see: http://www.coderpanda.com/jms-example-using-apache-activemq/
public class ActivemqSender
{
    private ConnectionFactory factory = null;

    private Connection connection = null;

    private Session session = null;

    private Destination destination = null;

    private MessageProducer producer = null;

    private static Logger LOG = LogManager.getLogger(ActivemqSender.class);

    //
    public ActivemqSender()
    {
        //
        try {
            factory = new ActiveMQConnectionFactory(ActivemqVariables.brokerUrl);
            connection = factory.createConnection();
            connection.start();
            LOG.info("Start activemq success with queueName:" + ActivemqVariables.queueName);
        } catch (JMSException e) {
            // e.printStackTrace();
            LOG.error(e.getMessage());
        }
    }

    // private static ActivemqSender instance = null;
    //
    // protected ActivemqSender(String queueName) {
    // // Exists only to defeat instantiation.
    // this.queueName = queueName;
    // }
    //
    // public static ActivemqSender getInstance(String queueName) {
    // if (instance == null) {
    // instance = new ActivemqSender(queueName);
    // }
    // return instance;
    // }

    public void sendMessage(String value)
    {
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(ActivemqVariables.queueName);
            producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();
            // message.setText("Hello ...This is a sample message..sending from FirstClient");
            message.setText(value);
            producer.send(message);
            // System.out.println("Sent: " + message.getText());
            LOG.info("Sent activemq message: " + message.getText() + ",queueName:" + ActivemqVariables.queueName);
        } catch (JMSException e) {
            // e.printStackTrace();
            LOG.error(e.getMessage());
        }
    }

}
