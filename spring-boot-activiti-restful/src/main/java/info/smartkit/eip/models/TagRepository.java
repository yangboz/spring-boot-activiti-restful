package info.smartkit.eip.models;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tags", path = "tags")
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {
	List<Tag> findById(@Param("id") long id);
}
