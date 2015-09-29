package info.smartkit.eip.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface ItemDao extends CrudRepository<Item, Long> {
	/**
	 * This method is not implemented and its working code will be
	 * auto-magically generated from its signature by Spring Data JPA.
	 *
	 * @param id
	 *            the item id.
	 * @return the item having the passed id or null if no item is found.
	 */
	public Item findById(long id);
	/**
	 * This method is not implemented and its working code will be
	 * auto-magically generated from its signature by Spring Data JPA.
	 *
	 * @param name
	 *            the item owner'name.
	 * @return the item having the passed owner'name or null if no item is found.
	 */
	//@see: http://ufasoli.blogspot.ca/2014/02/spring-boot-and-spring-data-jpa_6.html
	public Iterable<Item> findItemsByOwner(@Param("owner") String owner);
}
