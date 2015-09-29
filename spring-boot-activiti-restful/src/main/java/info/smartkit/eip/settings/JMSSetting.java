package info.smartkit.eip.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import info.smartkit.eip.activemq.ActivemqVariables;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "jms")
public class JMSSetting
{

    private static Logger LOG = LogManager.getLogger(JMSSetting.class);

    private String brokerUrl;

    public String getBrokerUrl()
    {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl)
    {
        this.brokerUrl = brokerUrl;
        //
        ActivemqVariables.brokerUrl = brokerUrl;
        LOG.info("brokerUrl:" + ActivemqVariables.brokerUrl);
    }
}
