/**
 * @author yangboz
 * @see http://blog.netgloo.com/2014/10/27/using-mysql-in-spring-boot-via-spring-data-jpa-and-hibernate/
 */
package info.smartkit.eip.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface CompanyDao extends CrudRepository<Company, Long> {
	/**
	 * This method is not implemented and its working code will be
	 * auto-magically generated from its signature by Spring Data JPA.
	 *
	 * @param domain
	 *            the company domain.
	 * @return the user having the passed domain or null if no company is found.
	 */
	public Company findByDomain(String domain);
}
