package info.smartkit.eip.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Transactional
//public interface ExpenseDao extends CrudRepository<Expense, Long> {
public interface ExpenseDao extends CrudRepository<Expense, String> {
	/**
	 * This method is not implemented and its working code will be
	 * auto-magically generated from its signature by Spring Data JPA.
	 *
	 * @param owner
	 *            the expense owner.
	 * @return the expense having the passed owner or null if no expense is
	 *         found.
	 */
	public Expense findById(long id);
	//@see: http://ufasoli.blogspot.ca/2014/02/spring-boot-and-spring-data-jpa_6.html
	public Iterable<Expense> findExpensesByOwner(@Param("owner") String owner);
}
