package info.smartkit.eip.settings;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
// Notice:Yaml properties later than properties injection.
// @ConfigurationProperties(prefix = "workflow")
public class WorkflowSetting
{
    //
    private static WorkflowSetting instance = null;

    protected WorkflowSetting()
    {
        // Exists only to defeat instantiation.
    }

    public static WorkflowSetting getInstance()
    {
        if (instance == null) {
            instance = new WorkflowSetting();
        }
        return instance;
    }

    //
    private String bpmn;

    public String getBpmn()
    {
        return bpmn;
    }

    public void setBpmn(String bpmn)
    {
        this.bpmn = bpmn;
    }

    private String image;

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    private String rule;

    public String getRule()
    {
        return rule;
    }

    public void setRule(String rule)
    {
        this.rule = rule;
    }

    private String name;// enableDuplicateFiltering().name

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
