package info.smartkit.eip.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vendors")
//@Embeddable
public class Vendor extends ModelBase {

	// ==============
	// PRIVATE FIELDS
	// ==============

	// An auto-generated id (unique for each user in the db)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long value) {
		this.id = value;
	}

	// The item name
	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// ==============
	// PUBLIC METHODS
	// ==============
	public Vendor() {
	}

	public Vendor(long id) {
		this.id = id;
	}

	public Vendor(String name) {
		this.name = name;
	}
}
