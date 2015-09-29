package info.smartkit.eip.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface TagDao extends CrudRepository<Tag, Long> {
	/**
	 * This method is not implemented and its working code will be
	 * auto-magically generated from its signature by Spring Data JPA.
	 *
	 * @param id
	 *            the tag id.
	 * @return the tag having the passed id or null if no tag is found.
	 */
	public Tag findById(long id);
}
