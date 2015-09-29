/*
 * Copyright 2005-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package info.smartkit.eip.ldap.odm.dao;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;

import info.smartkit.eip.ldap.plain.domain.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;

import info.smartkit.eip.ldap.plain.dao.PersonDao;

/**
 * Default implementation of PersonDao. This implementation uses the Object-Directory Mapping feature, which requires
 * the entity classes to be annotated, but relieves the programmer from the tedious task of mapping to and from entity
 * objects, using attribute or dn component values.
 * 
 * @author Mattias Hellborg Arthursson
 */
public class PersonDaoImpl implements PersonDao
{
    private static Logger LOG = LogManager.getLogger(PersonDaoImpl.class);

    private LdapTemplate ldapTemplate;

    @Override
    public LdapTemplate getLdapTemplate()
    {
        return ldapTemplate;
    }

    @Override
    public void create(Person person)
    {
        ldapTemplate.create(person);
    }

    @Override
    public void update(Person person)
    {
        ldapTemplate.update(person);
    }

    @Override
    public void delete(Person person)
    {
        ldapTemplate.delete(ldapTemplate.findByDn(buildDn(person), Person.class));
    }

    @Override
    public List<String> getAllPersonNames()
    {
        return ldapTemplate.search(query().attributes("cn").where("objectclass").is("person"),
            new AttributesMapper<String>()
            {
                @Override
                public String mapFromAttributes(Attributes attrs) throws NamingException
                {
                    return attrs.get("cn").get().toString();
                }
            });
    }

    @Override
    public List<String> getAllPersonUids()
    {
        return ldapTemplate.search(query().attributes("cn").where("objectclass").is("person"),
            new AttributesMapper<String>()
            {
                @Override
                public String mapFromAttributes(Attributes attrs) throws NamingException
                {
                    return attrs.get("uid").get().toString();
                }
            });
    }

    @Override
    public List<Person> findAll()
    {

        return ldapTemplate.findAll(Person.class);
    }

    @Override
    public List<Person> findAll(String baseOn)
    {
        List<Person> persons = ldapTemplate.find(query().base(baseOn).filter("(objectClass=person)"), Person.class);
        // List<Person> persons =
        // ldapTemplate.search(query().base(baseOn).filter("(objectClass=person)"), new PersonAttributesMapper());
        // LOG.info("ldap search query:" + query().toString());
        // for (Person person : persons) {
        // LOG.info("ldap searched person:" + person.toString());
        // }
        return persons;
        // return ldapTemplate.findAll(Person.class);
    }

    @Override
    public Person findByPrimaryKey(String country, String company, String fullname)
    {
        LdapName dn = buildDn(country, company, fullname);
        Person person = ldapTemplate.findByDn(dn, Person.class);

        return person;
    }

    @Override
    public Person findByUid(String uid)
    {
        Person person = ldapTemplate.findOne(query().where("uid").is(uid), Person.class);
        return person;
    }

    private LdapName buildDn(Person person)
    {
        return buildDn(person.getCn(), person.getOu(), person.getUid());
    }

    private LdapName buildDn(String country, String company, String completeName)
    {
        return LdapNameBuilder.newInstance().add("c", country).add("ou", company).add("cn", completeName).build();
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate)
    {
        this.ldapTemplate = ldapTemplate;
    }

}
