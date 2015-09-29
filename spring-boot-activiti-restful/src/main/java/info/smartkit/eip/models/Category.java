package info.smartkit.eip.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "categories")
//@Embeddable
public class Category extends ModelBase {
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

	// The category icon
	@NotNull
	private String icon;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	// The category name
	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	// The category label for displaying name.
	@NotNull
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	// @see: http://stackoverflow.com/questions/14388037/create-a-tree-using-jpa
	// This field is a table column
	// It identifies the parent of the current row
	// It it will be written as the type of categoryId
	// By default this relationship will be eagerly fetched
	// , which you may or may not want
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,CascadeType.MERGE })
//	@ManyToOne
    @JoinColumn(name="parent_id")
	@JsonBackReference
	private Category parent;

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	// This field is not a table column
	// It is a collection of those Category rows that have this row as a parent.
	// This is the other side of the relationship defined by the parent field.
//	@OneToMany(mappedBy = "parent")
	@OneToMany(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
	private Set<Category> children;

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	// ==============
	// PUBLIC METHODS
	// ==============

	public Category() {
	}

	public Category(long id) {
		this.id = id;
	}
	//Root category
	public Category(String icon, String name) {
		this.icon = icon;
		this.name = name;
	}
	//Child category
	public Category(String icon, String name, Category parent,
			Set<Category> children) {
		this.icon = icon;
		this.name = name;
		this.parent = parent;
		this.children = children;
	}
}
