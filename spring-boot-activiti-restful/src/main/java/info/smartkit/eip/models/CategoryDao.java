package info.smartkit.eip.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CategoryDao extends CrudRepository<Category, Long> {
	/**
	 * This method is not implemented and its working code will be
	 * auto-magically generated from its signature by Spring Data JPA.
	 *
	 * @param id
	 *            the category id.
	 * @return the user having the passed id or null if no category is found.
	 */
	public Category findById(long id);
}
