package info.smartkit.eip.settings;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class ServerSetting
{
    //
    private static ServerSetting instance = null;

    protected ServerSetting()
    {
        // Exists only to defeat instantiation.
    }

    public static ServerSetting getInstance()
    {
        if (instance == null) {
            instance = new ServerSetting();
        }
        return instance;
    }

    //
    private String contextPath;

    public String getContextPath()
    {
        return contextPath;
    }

    public void setContextPath(String contextPath)
    {
        this.contextPath = contextPath;
    }

    private String port;

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    // private String ip = "127.0.0.1";
    //
    // public String getIp()
    // {
    // return ip;
    // }
    //
    // public void setIp(String ip)
    // {
    // this.ip = ip;
    // }

    public String getUrl()
    {
        return "http://127.0.0.1:" + ServerSetting.getInstance().getPort()
            + ServerSetting.getInstance().getContextPath() + "/";
    }

}
