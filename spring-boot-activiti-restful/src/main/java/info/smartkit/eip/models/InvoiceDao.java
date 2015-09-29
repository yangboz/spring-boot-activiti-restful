package info.smartkit.eip.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface InvoiceDao extends CrudRepository<Invoice, Long> {
	/**
	 * This method is not implemented and its working code will be
	 * auto-magically generated from its signature by Spring Data JPA.
	 *
	 * @param id
	 *            the item id.
	 * @return the invoice having the passed id or null if no invoice is found.
	 */
	public Invoice findById(long id);
}
