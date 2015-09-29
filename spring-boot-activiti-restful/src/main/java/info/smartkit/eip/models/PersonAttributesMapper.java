package info.smartkit.eip.models;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import info.smartkit.eip.ldap.plain.domain.Person;
import org.springframework.ldap.core.AttributesMapper;

public class PersonAttributesMapper implements AttributesMapper<Person>
{
    @Override
    public Person mapFromAttributes(Attributes attrs) throws NamingException
    {
        Person person = new Person();
        person.setCn((String) attrs.get("cn").get());
        person.setSn((String) attrs.get("sn").get());
        person.setDescription((String) attrs.get("description").get());
        person.setUid((String) attrs.get("uid").get());
        person.setWxToken((String) attrs.get("postalAddress").get());
        person.setCode((String) attrs.get("postalCode").get());
        return person;
    }
}
