package info.smartkit.eip.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

import info.smartkit.eip.settings.ServerSetting;
import info.smartkit.eip.settings.WorkflowSetting;

/**
 * Register this with the DispatcherServlet in a ServletInitializer class like:
 * dispatcherServlet.setContextInitializers(new PropertiesInitializer());
 */
public class PropertiesInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
{
    private static final Logger LOG = LogManager.getLogger(PropertiesInitializer.class);

    /**
     * Runs as appInitializer so properties are wired before spring beans
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext)
    {
        ConfigurableEnvironment env = applicationContext.getEnvironment();

        String[] activeProfiles = getActiveProfiles(env);

        for (String profileName : activeProfiles) {
            LOG.info("Loading properties for Spring Active Profile: {}", profileName);
            try {
                ResourcePropertySource propertySource =
                    new ResourcePropertySource(profileName + "EnvProperties", "classpath:application-" + profileName
                        + ".properties");

                env.getPropertySources().addLast(propertySource);
                LOG.debug("propertySource:" + propertySource.toString());
                // Work-flow setting initialization here.
                // TODO: @see https://github.com/EsotericSoftware/yamlbeans to replace this staff.
                WorkflowSetting.getInstance().setBpmn((String) propertySource.getProperty("workflow.bpmn"));
                WorkflowSetting.getInstance().setName((String) propertySource.getProperty("workflow.name"));
                WorkflowSetting.getInstance().setImage((String) propertySource.getProperty("workflow.image"));
                WorkflowSetting.getInstance().setRule((String) propertySource.getProperty("workflow.rule"));
                //
                ServerSetting.getInstance().setContextPath((String) propertySource.getProperty("server.contextPath"));
                ServerSetting.getInstance().setPort((String) propertySource.getProperty("server.port"));
                // ServerSetting.getInstance().setIp((String) propertySource.getProperty("mserver.ip"));
            } catch (IOException e) {
                LOG.error("ERROR during environment properties setup - TRYING TO LOAD: " + profileName, e);

                // Okay to silently fail here, as we might have profiles that do
                // not have properties files (like dev1, dev2, etc)
            }
        }
    }

    /**
     * Returns either the ActiveProfiles, or if empty, then the DefaultProfiles from Spring
     */
    protected String[] getActiveProfiles(ConfigurableEnvironment env)
    {
        String[] activeProfiles = env.getActiveProfiles();
        if (activeProfiles.length > 0) {
            LOG.info("Using registered Spring Active Profiles: {}", StringUtils.join(activeProfiles, ", "));
            return activeProfiles;
        }

        String[] defaultProfiles = env.getDefaultProfiles();
        LOG.info("No Active Profiles found, using Spring Default Profiles: {}", StringUtils.join(defaultProfiles, ", "));
        return defaultProfiles;
    }

}
