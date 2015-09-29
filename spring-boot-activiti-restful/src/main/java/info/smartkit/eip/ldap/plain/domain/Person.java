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
package info.smartkit.eip.ldap.plain.domain;

import javax.naming.Name;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Simple class representing a single person.
 *
 * @author Mattias Hellborg Arthursson
 * @author Ulrik Sandberg
 * @see: http://oav.net/mirrors/LDAP-ObjectClasses.html
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entry(objectClasses = {"inetOrgPerson", "organizationalPerson", "person", "top"})
public class Person
{
    // @see:http://www.jayxu.com/2011/12/14/13131/
    // @JsonIgnoreProperties("dn")
    @Id
    private Name dn;

    @Attribute(name = "uid")
    @DnAttribute(value = "uid", index = 2)
    private String uid;

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    @Attribute(name = "cn")
    @DnAttribute(value = "cn", index = 1)
    private String cn;

    public String getCn()
    {
        return cn;
    }

    public void setCn(String cn)
    {
        this.cn = cn;
    }

    @Attribute(name = "sn")
    private String sn;

    public String getSn()
    {
        return sn;
    }

    public void setSn(String sn)
    {
        this.sn = sn;
    }

    @Attribute(name = "description")
    private String description;

    @Transient
    @DnAttribute(value = "ou", index = 0)
    private String ou;

    public String getOu()
    {
        return ou;
    }

    public void setOu(String ou)
    {
        this.ou = ou;
    }

    @Attribute(name = "mobile")
    private String mobile;

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    @JsonIgnore
    public Name getDn()
    {
        return dn;
    }

    public void setDn(Name dn)
    {
        this.dn = dn;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Attribute(name = "postalAddress")
    private String wxToken;

    public String getWxToken()
    {
        return wxToken;
    }

    public void setWxToken(String wxToken)
    {
        this.wxToken = wxToken;
    }

    @Attribute(name = "postalCode")
    private String code;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Attribute(name = "jpegPhoto")
    private String jpegPhoto;

    public String getJpegPhoto()
    {
        return jpegPhoto;
    }

    public void setJpegPhoto(String jpegPhoto)
    {
        this.jpegPhoto = jpegPhoto;
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
