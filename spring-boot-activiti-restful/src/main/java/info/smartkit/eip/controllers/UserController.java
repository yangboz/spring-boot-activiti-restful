package info.smartkit.eip.controllers;

import java.util.List;

import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.validation.Valid;

import info.smartkit.eip.models.SimplePerson;
import info.smartkit.eip.models.User;
import info.smartkit.eip.settings.LDAPSetting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.smartkit.eip.dto.JsonObject;
import info.smartkit.eip.ldap.plain.dao.PersonDao;
import info.smartkit.eip.ldap.plain.domain.Person;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
public class UserController
{
    //
    private static Logger LOG = LogManager.getLogger(UserController.class);

    // ==============
    // PRIVATE FIELDS
    // ==============
    @Autowired
    private PersonDao personDao;

    // @see
    // http://hoserdude.com/2014/06/19/spring-boot-configurationproperties-and-profile-management-using-yaml/
    @Autowired
    private LDAPSetting ldapSetting;

    // ==============
    // PUBLIC METHODS
    // ==============

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(httpMethod = "POST", value = "Response a string describing if the reimbursement company is successfully created or not.")
    public JsonObject create(@RequestBody @Valid SimplePerson simplePerson,
        @RequestParam(value = "group", required = true, defaultValue = "employees") String group,
        @RequestParam(value = "company", required = true, defaultValue = "www1.rushucloud.com") String company)
    {
        //
        BasicAttributes personAttributes = new BasicAttributes();
        BasicAttribute personBasicAttribute = new BasicAttribute("objectclass");
        // personBasicAttribute.add("person");
        personBasicAttribute.add("top");
        personBasicAttribute.add("extensibleObject");
        // this is the object class extension.
        // TODO:@see:
        // http://forum.spring.io/forum/spring-projects/data/ldap/90794-how-to-insert-multi-value-attribute-in-ad
        // personBasicAttribute.add("wxToken");
        // personBasicAttribute.add("code");
        personBasicAttribute.add("person");
        personBasicAttribute.add("organizationalperson");
        personBasicAttribute.add("inetorgperson");
        //
        personAttributes.put(personBasicAttribute);
        personAttributes.put("cn", "cn_" + simplePerson.getUsername());
        personAttributes.put("sn", "sn_" + simplePerson.getUsername());
        personAttributes.put("uid", simplePerson.getUsername());
        personAttributes.put("postalAddress", simplePerson.getWxToken());// wx_token
        personAttributes.put("postalCode", simplePerson.getCode());// code
        personAttributes.put("mobile", simplePerson.getMobile());
        personAttributes.put("mail", simplePerson.getEmail());
        personAttributes.put("userPassword", simplePerson.getPassword());
        // personAttributes.put("wxToken", simplePerson.getWxToken());
        // personAttributes.put("code", simplePerson.getCode());
        // BasicAttribute wxTokenAttribute = new BasicAttribute("wxToken", simplePerson.getWxToken());
        // wxTokenAttribute.add(simplePerson.getWxToken());
        // personAttributes.put("wxToken", wxTokenAttribute);
        // BasicAttribute codeAttribute = new BasicAttribute("code", simplePerson.getCode());
        // codeAttribute.add(simplePerson.getCode());
        // personAttributes.put("code", codeAttribute);
        personDao.getLdapTemplate().bind("uid=" + simplePerson.getUsername() + ",ou=" + group + ",ou=" + company, null,
            personAttributes);
        //
        return new JsonObject(simplePerson);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Response a list describing all of peron entry that is successfully get or not.")
    public JsonObject list(@AuthenticationPrincipal User user,
        @RequestParam(value = "groups", required = true, defaultValue = "employees,www1.rushucloud.com") String groups)
    {
        // List<Person> persons = personDao.findAll();
        String[] strGroups = groups.split(",");
        String plainGroups = "";
        for (int i = 0; i < strGroups.length; i++) {
            plainGroups += ",ou=" + strGroups[i];
        }
        List<Person> persons = personDao.findAll(plainGroups);
        return new JsonObject(persons);
    }

    @RequestMapping(value = "/{uid}", method = RequestMethod.PUT)
    @ApiOperation(httpMethod = "PUT", value = "Response a entry describing if the person entry is successfully updated or not.")
    public JsonObject update(@PathVariable("uid") String uid, @RequestBody @Valid Person person)
    {
        //
        personDao.update(person);
        return new JsonObject(personDao.findByUid(uid));
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ApiOperation(httpMethod = "DELETE", value = "Response a result describing if the peron entry is successfully delete or not.")
    public ResponseEntity<Boolean> delete(@RequestBody @Valid Person person)
    {
        personDao.delete(person);
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Response a entry describing if the person entry is successfully retrieved or not.")
    public JsonObject get(@PathVariable("uid") String uid)
    {
        //
        return new JsonObject(personDao.findByUid(uid));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    @ApiOperation(httpMethod = "GET", value = "LDAP client for authenticate user.")
    public boolean ldapAuthenticate(
        @RequestParam(value = "uid", required = true, defaultValue = "employee0") String uid,
        @RequestParam(value = "password", required = true, defaultValue = "passwordpassword") String password,
        @RequestParam(value = "groups", required = true, defaultValue = "employees,www1.rushucloud.com") String groups)
    {
        String[] strGroups = groups.split(",");
        String plainGroups = "";
        for (int i = 0; i < strGroups.length; i++) {
            plainGroups += ",ou=" + strGroups[i];
        }
        return personDao.getLdapTemplate().authenticate("uid=" + uid + plainGroups, "(objectClass=Person)", password);
    }
}
