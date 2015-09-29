package info.smartkit.eip.settings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import info.smartkit.eip.ldap.odm.dao.PersonDaoImpl;

//@see https://docs.oracle.com/javase/tutorial/jndi/ldap/jndi.html
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ldap")
public class LDAPSetting
{
    private static Logger LOG = LogManager.getLogger(LDAPSetting.class);

    private String url;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    private String userOn;

    public String getUserOn()
    {
        return userOn;
    }

    public void setUserOn(String userOn)
    {
        this.userOn = userOn;
    }

    private String password;

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    private String base;

    public String getBase()
    {
        return base;
    }

    public void setBase(String base)
    {
        this.base = base;
    }

    //
    @Override
    public String toString()
    {
        return "url:" + this.url + ",userOn:" + this.userOn + ",base:" + this.base + ",password:" + this.password;
    }

    // @see: http://stackoverflow.com/questions/26004062/ldap-query-configuration-using-spring-boot
    @Bean
    @ConfigurationProperties(prefix = "ldap")
    public LdapContextSource contextSource()
    {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(this.getUrl());
        contextSource.setUserDn(this.getUserOn());
        contextSource.setPassword(this.getPassword());
        try {
            contextSource.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.debug("LdapContextSource:" + this.toString());
        // LdapContextSource contextSource = new LdapContextSource();
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource)
    {
        return new LdapTemplate(contextSource);
    }

    @Bean
    public PersonDaoImpl personDaoImpl(LdapTemplate ldapTemplate)
    {
        PersonDaoImpl personRepo = new PersonDaoImpl();
        personRepo.setLdapTemplate(ldapTemplate);
        return personRepo;
    }
}
