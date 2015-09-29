package info.smartkit.eip.models;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invoices")
//@Embeddable
public class Invoice extends OwnerModelBase {

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

	// The invoice name(default picture file name)
	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// The invoice picture
	// TODO:big/middle/small size generation.
	@NotNull
	@ElementCollection
	Map<String,String> picture;
//	private String picture;
//
//	public String getPicture() {
//		return picture;
//	}
//
//	public void setPicture(String picture) {
//		this.picture = picture;
//	}

	public Map<String, String> getPicture() {
		return picture;
	}

	public void setPicture(Map<String, String> picture) {
		this.picture = picture;
	}

	// ==============
	// PUBLIC METHODS
	// ==============
	public Invoice() {
	}

	public Invoice(long id) {
		this.id = id;
	}

	public Invoice(String name, String owner,Map<String,String> picture) {
		this.name = name;
		this.owner = owner;
		this.picture = picture;
	}
}
