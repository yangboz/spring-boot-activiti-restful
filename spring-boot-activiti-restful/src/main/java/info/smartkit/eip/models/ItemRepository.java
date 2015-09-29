package info.smartkit.eip.models;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="items",path="items")
public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {
	List<Item> findById(@Param("id") long id);
//	List<Item> findByOwner(@Param("owner") String owner);
}