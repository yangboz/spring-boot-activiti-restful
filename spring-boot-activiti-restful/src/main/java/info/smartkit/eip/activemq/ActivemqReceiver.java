package info.smartkit.eip.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import info.smartkit.eip.settings.JMSSetting;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

//@see: http://www.coderpanda.com/jms-example-using-apache-activemq/
public class ActivemqReceiver
{
    private ConnectionFactory factory = null;

    private Connection connection = null;

    private Session session = null;

    private Destination destination = null;

    private MessageConsumer consumer = null;

    private static Logger LOG = LogManager.getLogger(ActivemqReceiver.class);

    @Autowired
    private JMSSetting jmsSetting;

    public ActivemqReceiver()
    {
    }

    // private static ActivemqReceiver instance = null;
    //
    // protected ActivemqReceiver(String queueName) {
    // // Exists only to defeat instantiation.
    // this.queueName = queueName;
    // }
    //
    // public static ActivemqReceiver getInstance(String queueName) {
    // if (instance == null) {
    // instance = new ActivemqReceiver(queueName);
    // }
    // return instance;
    // }

    public void receiveMessage()
    {
        try {
            factory = new ActiveMQConnectionFactory(jmsSetting.getBrokerUrl());
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(ActivemqVariables.queueName);
            consumer = session.createConsumer(destination);
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                TextMessage text = (TextMessage) message;
                // System.out.println("Message is : " + text.getText());
                LOG.info("Received activemq message is : " + text.getText() + ",queueName:"
                    + ActivemqVariables.queueName);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
