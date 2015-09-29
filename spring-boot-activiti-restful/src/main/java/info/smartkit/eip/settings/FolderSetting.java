package info.smartkit.eip.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "folder")
public class FolderSetting
{
    private String uploads;

    public String getUploads()
    {
        return uploads;
    }

    public void setUploads(String uploads)
    {
        this.uploads = uploads;
    }

    private String reports;

    public String getReports()
    {
        return reports;
    }

    public void setReports(String reports)
    {
        this.reports = reports;
    }

}
