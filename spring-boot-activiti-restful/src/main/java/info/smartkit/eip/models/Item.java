package info.smartkit.eip.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "items")
public class Item extends OwnerModelBase {
	// ApproveAhead,CostComsumed
	public enum ItemType {
		ApproveAhead, CostConsumed
	}

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

	// The item money amount
	@NotNull
	private double amount;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	// The item type
	@Enumerated(EnumType.STRING)
	private ItemType type;

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	// The item invoices,id strings
	// @CollectionOfElements,@see:
	// https://jazzy.id.au/2008/03/24/jpa_2_0_new_features_part_1.html
	 private String invoices = "1";// sort of invoice ids:"1,2,3,4"
//	@ElementCollection
//	@CollectionTable(name = "item_invoices", joinColumns = @JoinColumn(name = "itemId"))
//	private Set<Invoice> invoices = new HashSet<Invoice>();
//
//	public Set<Invoice> getInvoices() {
//		return invoices;
//	}
//
//	public void setInvoices(Set<Invoice> invoices) {
//		this.invoices = invoices;
//	}

	public String getInvoices() {
		return invoices;
	}

	public void setInvoices(String invoices) {
		this.invoices = invoices;
	}

	// The item vendors,id strings
	 private String vendors = "1";// sort of vendor ids:"1,2,3,4"
	// @see:
	// http://www.thejavageek.com/2014/01/31/jpa-elementcollection-annotation/
//	@ElementCollection
//	@CollectionTable(name = "item_vendors", joinColumns = @JoinColumn(name = "itemId"))
//	private Set<Vendor> vendors = new HashSet<Vendor>();
//
//	public Set<Vendor> getVendors() {
//		return vendors;
//	}
//
//	public void setVendors(Set<Vendor> vendors) {
//		this.vendors = vendors;
//	}

	public String getVendors() {
		return vendors;
	}

	public void setVendors(String vendors) {
		this.vendors = vendors;
	}

	// The item category,id strings.
	 private String category = "1";//one category id:"1"
//	@ElementCollection
//	@CollectionTable(name = "item_categories", joinColumns = @JoinColumn(name = "itemId"))
//	private Set<Category> categories = new HashSet<Category>();
//
//
//	public Set<Category> getCategories() {
//		return categories;
//	}
//
//	public void setCategories(Set<Category> categories) {
//		this.categories = categories;
//	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	//Vendor related place.
	private String place = "";
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	// The expense involves,id strings
	// @CollectionOfElements,@see:
	// https://jazzy.id.au/2008/03/24/jpa_2_0_new_features_part_1.html
	private String participantIds;// sort of participant IDs:"1,2,3,4"

	public String getParticipantIds() {
		return participantIds;
	}

	public void setParticipantIds(String participantIds) {
		this.participantIds = participantIds;
	}
	
	private String notes;
	
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	/*
	 * Indicate whether or not used.
	 */
	private Boolean used=false;

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	// ==============
	// PUBLIC METHODS
	// ==============
	public Item() {
	}

	public Item(long id) {
		this.id = id;
	}

//	public Item(double amount, String name, ItemType type, Date date,
//			Set<Invoice> invoices, Set<Vendor> vendors, String owner,
//			Set<Category> category) {
	public Item(double amount, String name, ItemType type, Date date,
			String invoices, String vendors, String owner, String place,
			String category, String participantIds,String notes) {	
		this.amount = amount;
		this.name = name;
		this.type = type;
		this.date = date;
		this.invoices = invoices;
		this.vendors = vendors;
		this.owner = owner;
		this.category = category;
		this.participantIds = participantIds;
		this.notes = notes;
		this.place = place;
	}
}
