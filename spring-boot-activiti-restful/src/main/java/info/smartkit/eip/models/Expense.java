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

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "expenses")
//@Embeddable
//Override the default Hibernation delete and set the deleted flag rather than deleting the record from the db.
@SQLDelete(sql="UPDATE expenses SET deleted = '1' WHERE id = ?")
//Filter added to retrieve only records that have not been soft deleted.
@Where(clause="deleted <> '1'")
public class Expense extends OwnerModelBase {
	//
	public enum ExpenseStatus {
		Approved, Saved, Submitted, Rejected, Completed
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

	// The expenses money amount(of expense item)
	@NotNull
	private double amount;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	// The expense name
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
	private ExpenseStatus status;

	public ExpenseStatus getStatus() {
		return status;
	}

	public void setStatus(ExpenseStatus status) {
		this.status = status;
	}

	// report manager id.
	@NotNull
	private String managerId;

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
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

	//
	@NotNull
	private String itemIds;// sort of expense item IDs:"1,2,3,4"

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	
	//Activiti process instance id
	private long pid;
	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}
	//Soft-delete
	//@see: http://featurenotbug.com/2009/07/soft-deletes-using-hibernate-annotations/
	private char deleted='0';
	public char getDeleted() {
		return deleted;
	}

	public void setDeleted(char deleted) {
		this.deleted = deleted;
	}

	// ==============
	// PUBLIC METHODS
	// ==============
	public Expense() {
	}

	public Expense(long id) {
		this.id = id;
	}

	public Expense(double amount, String name, ExpenseStatus status, Date date,
			String owner, String managerId, String participantIds,
			String itemIds,long pid) {
		this.amount = amount;
		this.name = name;
		this.status = status;
		this.date = date;
		this.owner = owner;
		this.managerId = managerId;
		this.participantIds = participantIds;
		this.itemIds = itemIds;
		this.pid = pid;
	}
}
