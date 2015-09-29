package info.smartkit.eip.models;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="category",path="category")
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
	List<Category> findById(@Param("id") long id);
}
