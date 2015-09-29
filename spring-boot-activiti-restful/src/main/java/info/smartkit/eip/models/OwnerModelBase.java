package info.smartkit.eip.models;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class OwnerModelBase extends ModelBase{
	// The base owner name or id.
	@NotNull
	protected String owner;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
