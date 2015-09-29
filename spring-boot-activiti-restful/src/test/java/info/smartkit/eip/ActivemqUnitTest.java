package info.smartkit.eip;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.Stomp.Headers.Subscribe;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;
import org.junit.Test;

public class ActivemqUnitTest {

	@Test
	public void test() throws Exception{
		StompConnection connection = new StompConnection();
		connection.open("localhost", 61613);
		         
		connection.connect("system", "manager");
		StompFrame connect = connection.receive();
		if (!connect.getAction().equals(Stomp.Responses.CONNECTED)) {
		    throw new Exception ("Not connected");
		}
		         
		connection.begin("tx1");
		connection.send("/queue/test", "message1", "tx1", null);
		connection.send("/queue/test", "message2", "tx1", null);
		connection.commit("tx1");
		     
		connection.subscribe("/queue/test", Subscribe.AckModeValues.CLIENT);
		     
		connection.begin("tx2");
		     
		StompFrame message = connection.receive();
		System.out.println(message.getBody());
		connection.ack(message, "tx2");
		     
		message = connection.receive();
		System.out.println(message.getBody());
		connection.ack(message, "tx2");
		     
		connection.commit("tx2");
		         
		connection.disconnect();
	}
}
