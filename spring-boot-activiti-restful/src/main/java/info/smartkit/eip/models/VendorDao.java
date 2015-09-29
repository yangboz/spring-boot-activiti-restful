package info.smartkit.eip.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface VendorDao extends CrudRepository<Vendor, Long> {
	/**
	 * This method is not implemented and its working code will be
	 * auto-magically generated from its signature by Spring Data JPA.
	 *
	 * @param id
	 *            the vendor id.
	 * @return the vendor having the passed id or null if no vendor is found.
	 */
	public Vendor findById(long id);
}
