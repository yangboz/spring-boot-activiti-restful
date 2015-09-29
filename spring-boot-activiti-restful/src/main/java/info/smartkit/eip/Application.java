package info.smartkit.eip;

import info.smartkit.eip.activemq.ActivemqVariables;
import info.smartkit.eip.config.PropertiesInitializer;
import info.smartkit.eip.settings.WorkflowSetting;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
//
@PropertySources({@PropertySource(value = "classpath:application-${spring.profiles.active}.properties")})
//
@ComponentScan("info.smartkit.eip")
// @EnableWebSecurity
@EnableAutoConfiguration
// @EnableAutoConfiguration(exclude={WebSocketAutoConfiguration.class,JpaProcessEngineAutoConfiguration.class})
//
@ImportResource("classpath:activiti-standalone-context-${spring.profiles.active}.xml")
// @see: http://spring.io/guides/gs/accessing-data-rest/
@EnableJpaRepositories
//
@Import(RepositoryRestMvcConfiguration.class)
//
public class Application extends SpringBootServletInitializer {
    //
    private static Logger LOG = LogManager.getLogger(Application.class);

    //
    private static Class<Application> applicationClass = Application.class;

    //
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //
        return application.sources(applicationClass);
    }

    //
    public static void main(String[] args) throws InterruptedException {
        //
        ApplicationContext context =
                new SpringApplicationBuilder(applicationClass).initializers(new PropertiesInitializer()).run(args);
        // SpringApplication.run(applicationClass, args);
        LOG.info("ApplicationContext:" + context.getDisplayName() + context.getStartupDate());
        //
        workflowInitialization();
        //
        // System.setProperty("javax.xml.parsers.SAXParserFactory","com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
        //
        System.setProperty("javax.xml.parsers.SAXParserFactory", "org.apache.xerces.jaxp.SAXParserFactoryImpl");
    }

    //
    public static void workflowInitialization() {
        //
        LOG.info("workflowInitialization..." + WorkflowSetting.getInstance().getBpmn());
        // Deploying the process here,avoid duplication to see:
        // http://forums.activiti.org/content/duplicate-deployment-processes
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // Setting the process deployment with configuration.
        LOG.info("workflowSetting.getName():" + WorkflowSetting.getInstance().getName() + ",workflowSetting.getBpmn():"
                + WorkflowSetting.getInstance().getBpmn() + ",workflowSetting.getImage():"
                + WorkflowSetting.getInstance().getImage());
        // @see:
        repositoryService.createDeployment().addClasspathResource(WorkflowSetting.getInstance().getBpmn())
                .addClasspathResource(WorkflowSetting.getInstance().getImage()).enableDuplicateFiltering()
                .name(WorkflowSetting.getInstance().getName()).deploy();
        // Log information
        LOG.info("Process definitions: " + repositoryService.createProcessDefinitionQuery().list().toString());
        LOG.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
        /*
         * //Starting a process instance Map<String,Object> variables = new HashMap<String,Object>();
         * variables.put("employeeName", "employee1"); variables.put("amountOfMoney", (long)99.8);
         * variables.put("reimbursmentMotivation", "Need reimbursement for taxi."); // RuntimeService runtimeService =
         * processEngine.getRuntimeService(); ProcessInstance processInstance = runtimeService
         * .startProcessInstanceByKey("reimbursementRequest",variables); //Verify that we started anew process instance
         * LOG.info("Process instance:"+processInstance.getId()); LOG.info("Process instances:"
         * +runtimeService.createProcessInstanceQuery().list().toString()); LOG.info
         * ("Number of process instances:"+runtimeService.createProcessInstanceQuery ().count());
         */
        // ActiveMQ message receiver
        // ActivemqSender sender = ActivemqSender.getInstance("SAMPLEQUEUE");
        String businessKey = repositoryService.createProcessDefinitionQuery().list().get(0).getKey();
        String processDefinitionId = repositoryService.createProcessDefinitionQuery().list().get(0).getId();
        String activemqChannelName = businessKey + "/" + processDefinitionId;
        // Save the queueName.
        ActivemqVariables.channelName = activemqChannelName;
        LOG.info("ActiveMQ initializing with channel name:" + ActivemqVariables.channelName);
        // ActivemqSender sender = new ActivemqSender(activemqQueueName);
        // sender.sendMessage("echo");//For testing
        // ActivemqReceiver receiver = new ActivemqReceiver("SAMPLEQUEUE");
        // ActivemqReceiver receiver =
        // ActivemqReceiver.getInstance("SAMPLEQUEUE");
        // receiver.receiveMessage();
        // Generate basic data base.
        // // EntityManager entityManager =
        // Persistence.createEntityManagerFactory("activiti_test").createEntityManager();
        // entityManager.getTransaction().begin();
        // Company company = new Company();
        // company.setBusinessKey(businessKey);
        // company.setDate(new Date());
        // company.setDomain("example.com");
        // company.setEmail("sample@example.com");
        // company.setName("EXAMPLE.COM");
        // entityManager.persist(company);
        // entityManager.getTransaction().commit();
    }

    // @see:
    // http://stackoverflow.com/questions/26425067/resolvedspring-boot-access-to-entitymanager
    @Bean
    public PersistenceAnnotationBeanPostProcessor persistenceBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    // @see:
    // http://stackoverflow.com/questions/23446928/spring-boot-uploading-files-path
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

}
