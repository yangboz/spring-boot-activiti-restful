/**
 * @author yangboz
 * @see http://blog.netgloo.com/2014/10/27/using-mysql-in-spring-boot-via-spring-data-jpa-and-hibernate/
 */
package info.smartkit.eip.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "companies")
public class Company  extends ModelBase{
//public class Company{
	// ==============
	// PRIVATE FIELDS
	// ==============

	// An auto-generated id (unique for each user in the db)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id = 0;

	public long getId() {
		return id;
	}

	public void setId(long value) {
		this.id = value;
	}

	// The company email
	@NotNull
	private String email = "demo@rushucloud.com";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// The company domain
	@NotNull
	private String domain = "rushucloud.com";

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	// The company name
	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	// The company business keys
	@NotNull
	private String businessKey = "reimbursementRequest";//default value for Reimbursement.
	//
	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	// @see: http://stackoverflow.com/questions/2572566/java-jpa-version-annotation
	@Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;
	
	// ==============
	// PUBLIC METHODS
	// ==============

	public Company() {
	}

	public Company(long id) {
		this.id = id;
	}

	public Company(String email, String name, String domain,String businessKey) {
		this.email = email;
		this.name = name;
		this.domain = domain;
		this.businessKey = businessKey;
	}
}
