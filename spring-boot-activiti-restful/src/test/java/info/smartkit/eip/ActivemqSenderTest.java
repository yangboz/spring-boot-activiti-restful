package info.smartkit.eip;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

//@see: http://www.coderpanda.com/jms-example-using-apache-activemq/
public class ActivemqSenderTest {
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;

	public ActivemqSenderTest() {
	}

	public void sendMessage() {
		try {
			factory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("SAMPLEQUEUE");
			producer = session.createProducer(destination);
			TextMessage message = session.createTextMessage();
			message.setText("Hello ...This is a sample message..sending from FirstClient");
			producer.send(message);
			System.out.println("Sent: " + message.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ActivemqSenderTest sender = new ActivemqSenderTest();
		sender.sendMessage();
	}
}
