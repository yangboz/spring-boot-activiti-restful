package info.smartkit.eip.models;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="expenses",path="expenses")
public interface ExpenseRepository extends PagingAndSortingRepository<Expense, Long> {
	List<Expense> findById(@Param("id") long id);
}